console.log("sidebar.js carregado");
document.addEventListener("DOMContentLoaded", () => {
  const items = document.querySelectorAll(".menu-item > p");
  items.forEach(item => {
    item.addEventListener("click", () => {
      const submenu = item.nextElementSibling;
      const isVisible = submenu.style.display === "block";

      document.querySelectorAll(".submenu").forEach(s => s.style.display = "none");
      submenu.style.display = isVisible ? "none" : "block";
    });
  });
});