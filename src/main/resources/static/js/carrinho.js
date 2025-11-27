(() => {
  const KEY = 'ticketer_cart';

  function read() {
    try {
      return JSON.parse(localStorage.getItem(KEY) || '[]');
    } catch (e) {
      console.error('Failed to parse cart', e);
      return [];
    }
  }

  function write(cart) {
    localStorage.setItem(KEY, JSON.stringify(cart));
    updateBadge();
  }

  function addItem(item) {
    const cart = read();
    const existing = cart.find(i => i.id === item.id);
    if (existing) {
      existing.quantity = (existing.quantity || 0) + (item.quantity || 1);
    } else {
      cart.push({ id: item.id, name: item.name, price: Number(item.price) || 0, quantity: item.quantity || 1 });
    }
    write(cart);
  }

  function updateQuantity(id, quantity) {
    const cart = read();
    const it = cart.find(i => i.id === id);
    if (!it) return;
    it.quantity = Math.max(0, Number(quantity) || 0);
    const newCart = cart.filter(i => i.quantity > 0);
    write(newCart);
  }

  function removeItem(id) {
    const cart = read().filter(i => i.id !== id);
    write(cart);
  }

  function clear() {
    localStorage.removeItem(KEY);
    updateBadge();
  }

  function getItems() {
    return read();
  }

  function total() {
    return read().reduce((s, i) => s + (Number(i.price) || 0) * (Number(i.quantity) || 0), 0);
  }

  function updateBadge() {
    const badge = document.getElementById('cart-count');
    if (!badge) return;
    const count = read().reduce((s, i) => s + (Number(i.quantity) || 0), 0);
    badge.textContent = count || '';
  }

  // DOM helpers: attach to all "add to cart" buttons (class .btn-comprar)
  function initAddButtons() {
    document.querySelectorAll('.btn-comprar').forEach(btn => {
      btn.addEventListener('click', (e) => {
        e.preventDefault();
        const el = btn;
        // read data attributes or find sibling quantity input
        const id = el.dataset.id || el.dataset.sku || el.getAttribute('data-name') || el.textContent.trim();
        let name = el.dataset.name || el.getAttribute('data-name') || id;
        const price = Number(el.dataset.price || el.getAttribute('data-price') || 0);
        let qty = Number(el.dataset.quantity || 0);
        if (!qty) {
          // try to find an input[type=number] nearby
          const qInput = el.closest('.filme-card')?.querySelector('input[type="number"]');
          qty = qInput ? Number(qInput.value || 1) : 1;
        }

        // Check if this is an ingresso (ticket) - try to find poltrona input
        const filmeCard = el.closest('.filme-card');
        if (filmeCard) {
          const poltronaInput = filmeCard.querySelector('input[type="text"]');
          if (poltronaInput && poltronaInput.value) {
            name = name + ' - Poltrona: ' + poltronaInput.value;
          }
        }

        addItem({ id, name, price, quantity: qty });
        // optional: simple feedback
        el.textContent = 'Adicionado ✓';
        setTimeout(() => { el.textContent = 'Comprar Ingresso'; }, 900);
      });
    });
  }

  // Render cart in #cart-container (in carrinho.html)
  function renderCart() {
    const container = document.getElementById('cart-container');
    if (!container) return;
    const items = getItems();
    container.innerHTML = '';
    if (items.length === 0) {
      container.innerHTML = '<p>Seu carrinho está vazio.</p>';
      updateBadge();
      return;
    }

    const table = document.createElement('table');
    table.className = 'table';
    table.innerHTML = `
      <thead><tr><th>Item</th><th>Preço</th><th>Quantidade</th><th>Subtotal</th><th></th></tr></thead>
    `;
    const tbody = document.createElement('tbody');
    items.forEach(it => {
      const tr = document.createElement('tr');
      // Oculta o nome técnico e mostra apenas "Ingresso" ou nome genérico
      const displayName = it.name && it.name.startsWith('Ingresso') ? 'Ingresso' : 'Produto';
      tr.innerHTML = `
        <td>${escapeHtml(displayName)}</td>
        <td>R$ ${Number(it.price).toFixed(2)}</td>
        <td><input type="number" min="1" value="${Number(it.quantity)}" class="cart-qty form-control" data-id="${escapeHtml(it.id)}" style="width: 80px;"></td>
        <td>R$ ${(Number(it.price) * Number(it.quantity)).toFixed(2)}</td>
        <td><button class="btn btn-sm btn-danger cart-remove" data-id="${escapeHtml(it.id)}">Remover</button></td>
      `;
      tbody.appendChild(tr);
    });
    table.appendChild(tbody);
    const footer = document.createElement('div');
    footer.className = 'mt-3';
    footer.innerHTML = `<strong>Total: R$ ${total().toFixed(2)}</strong> <button id="clear-cart" class="btn btn-sm btn-secondary ms-3">Limpar carrinho</button>`;
    container.appendChild(table);
    container.appendChild(footer);

    // show checkout button only if cart is not empty
    const checkoutBtn = document.getElementById('checkout-btn');
    if (checkoutBtn) {
      checkoutBtn.style.display = 'block';
    }

    // attach events
    container.querySelectorAll('.cart-remove').forEach(b => {
      b.addEventListener('click', () => {
        removeItem(b.dataset.id);
        renderCart();
      });
    });
    container.querySelectorAll('.cart-qty').forEach(input => {
      input.addEventListener('change', () => {
        const id = input.dataset.id;
        const q = Math.max(0, Number(input.value) || 0);
        updateQuantity(id, q);
        renderCart();
      });
    });
    document.getElementById('clear-cart')?.addEventListener('click', () => {
      clear();
      renderCart();
    });

    updateBadge();
  }

  // Checkout function
  function checkout() {
    const items = getItems();
    if (items.length === 0) {
      alert('Seu carrinho está vazio!');
      return;
    }

    const checkoutBtn = document.getElementById('checkout-btn');
    const statusDiv = document.getElementById('checkout-status');
    
    if (!checkoutBtn || !statusDiv) return;

    checkoutBtn.disabled = true;
    checkoutBtn.textContent = 'Processando...';
    statusDiv.innerHTML = '';

    fetch('/api/checkout', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ items: items })
    })
      .then(resp => {
        if (!resp.ok) {
          return resp.text().then(text => {
            throw new Error(text || 'Erro ao processar checkout');
          });
        }
        return resp.json();
      })
      .then(data => {
        // Success: clear cart and show message
        statusDiv.innerHTML = `
          <div class="alert alert-success" role="alert">
            <strong>Compra realizada com sucesso!</strong>
            <p>Venda #${data.id} - Total: R$ ${Number(data.preco).toFixed(2)}</p>
            <p><a href="/historico-vendas" class="btn btn-sm btn-primary">Ver no histórico</a></p>
          </div>
        `;
        clear();
        renderCart();
        checkoutBtn.disabled = false;
        checkoutBtn.textContent = 'Finalizar Compra';
      })
      .catch(err => {
        statusDiv.innerHTML = `<div class="alert alert-danger" role="alert">Erro ao processar compra: ${escapeHtml(err.message)}</div>`;
        checkoutBtn.disabled = false;
        checkoutBtn.textContent = 'Finalizar Compra';
      });
  }

  // small helper to avoid XSS when injecting text
  function escapeHtml(s) {
    return String(s)
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/"/g, '&quot;')
      .replace(/'/g, '&#039;');
  }

  // initialize everything on DOMContentLoaded
  document.addEventListener('DOMContentLoaded', () => {
    initAddButtons();
    renderCart();
    updateBadge();
    
    // attach checkout button listener
    const checkoutBtn = document.getElementById('checkout-btn');
    if (checkoutBtn) {
      checkoutBtn.addEventListener('click', checkout);
    }
    
    // listen for storage events so multiple tabs stay in sync
    window.addEventListener('storage', (e) => {
      if (e.key === KEY) {
        updateBadge();
        renderCart();
      }
    });
  });

  // Expose some functions for debugging on window
  window.TicketerCart = { addItem, getItems, clear, removeItem, updateQuantity, total, checkout };
})();