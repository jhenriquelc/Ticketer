-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Ticketer
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Ticketer
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Ticketer` ;
USE `Ticketer` ;

-- -----------------------------------------------------
-- Table `Ticketer`.`Filme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ticketer`.`Filme` (
  `idFilme` INT NOT NULL AUTO_INCREMENT,
  `duracao` TIME NOT NULL,
  `precoIngresso` FLOAT NOT NULL,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`idFilme`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ticketer`.`Venda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ticketer`.`Venda` (
  `idVenda` INT NOT NULL AUTO_INCREMENT,
  `data` TIMESTAMP NOT NULL,
  PRIMARY KEY (`idVenda`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ticketer`.`ItemVenda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ticketer`.`ItemVenda` (
  `idItemVenda` INT NOT NULL AUTO_INCREMENT,
  `preco` FLOAT NOT NULL,
  `descricao` VARCHAR(50) NOT NULL,
  `fk_idVenda` INT NOT NULL,
  PRIMARY KEY (`idItemVenda`),
  INDEX `fk_idVenda_idx` (`fk_idVenda` ASC) VISIBLE,
  CONSTRAINT `fk_idVenda`
    FOREIGN KEY (`fk_idVenda`)
    REFERENCES `Ticketer`.`Venda` (`idVenda`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ticketer`.`Sala`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ticketer`.`Sala` (
  `idSala` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idSala`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ticketer`.`Exibicao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ticketer`.`Exibicao` (
  `idExibicao` INT NOT NULL AUTO_INCREMENT,
  `fk_idSala` INT NOT NULL,
  `fk_idFilme` INT NOT NULL,
  `inicio` TIMESTAMP NOT NULL,
  PRIMARY KEY (`idExibicao`),
  INDEX `fk_idSala_idx` (`fk_idSala` ASC) VISIBLE,
  INDEX `fk_idFilme_idx` (`fk_idFilme` ASC) VISIBLE,
  CONSTRAINT `fk_idSala`
    FOREIGN KEY (`fk_idSala`)
    REFERENCES `Ticketer`.`Sala` (`idSala`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_idFilme`
    FOREIGN KEY (`fk_idFilme`)
    REFERENCES `Ticketer`.`Filme` (`idFilme`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ticketer`.`Ingresso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ticketer`.`Ingresso` (
  `idIngresso` INT NOT NULL AUTO_INCREMENT,
  `fk_idExibicao` INT NOT NULL,
  `assento` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`idIngresso`),
  INDEX `fk_idExibicao_idx` (`fk_idExibicao` ASC) VISIBLE,
  CONSTRAINT `fk_idExibicao`
    FOREIGN KEY (`fk_idExibicao`)
    REFERENCES `Ticketer`.`Exibicao` (`idExibicao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ticketer`.`ItemBomboniere`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ticketer`.`ItemBomboniere` (
  `idItemBomboniere` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(50) NOT NULL,
  `preco` FLOAT NOT NULL,
  PRIMARY KEY (`idItemBomboniere`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
