CREATE SCHEMA IF NOT EXISTS `banksystem` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `banksystem`;

CREATE TABLE IF NOT EXISTS `banksystem`.`bank_account` (
  `bank_account_id` INT NOT NULL AUTO_INCREMENT,
  `balance` DECIMAL(10,0) NOT NULL,
  `currency` VARCHAR(10) NOT NULL,
  `is_blocked` TINYINT NOT NULL,
  PRIMARY KEY (`bank_account_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `banksystem`.`passport_data` (
  `passport_id` INT NOT NULL AUTO_INCREMENT,
  `seria_and_number` VARCHAR(9) NOT NULL,
  `personal_number` VARCHAR(14) NOT NULL,
  `expiration_date` DATE NOT NULL,
  PRIMARY KEY (`passport_id`),
  UNIQUE INDEX `personal_number_UNIQUE` (`personal_number` ASC) VISIBLE,
  UNIQUE INDEX `seria_and_number_UNIQUE` (`seria_and_number` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
PACK_KEYS = 0;

CREATE TABLE IF NOT EXISTS `banksystem`.`role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `banksystem`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(40) NULL,
  `second_name` VARCHAR(60) NULL,
  `phone_number` VARCHAR(9) NULL,
  `age` INT NULL,
  `gender` VARCHAR(20) NULL,
  `client_id` INT NOT NULL,
  `passport_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_user_client1_idx` (`client_id` ASC) VISIBLE,
  INDEX `fk_user_passport_data1_idx` (`passport_id` ASC) VISIBLE,
  INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `banksystem`.`client` (`client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_passport_data1`
    FOREIGN KEY (`passport_id`)
    REFERENCES `banksystem`.`passport_data` (`passport_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `banksystem`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `banksystem`.`credit_card` (
  `credit_card_id` INT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(16) NOT NULL,
  `expiration_date` DATE NOT NULL,
  `full_name` VARCHAR(70) NOT NULL,
  `cvv` VARCHAR(3) NOT NULL,
  `pin` VARCHAR(4) NOT NULL,
  `user_id` INT NOT NULL,
  `bank_account_id` INT NOT NULL,
  PRIMARY KEY (`credit_card_id`),
  INDEX `fk_credit_card_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_credit_card_bank_account1_idx` (`bank_account_id` ASC) VISIBLE,
  CONSTRAINT `fk_credit_card_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `banksystem`.`user` (`user_id`),
  CONSTRAINT `fk_credit_card_bank_account1`
    FOREIGN KEY (`bank_account_id`)
    REFERENCES `banksystem`.`bank_account` (`bank_account_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `banksystem`.`payment` (
  `payment_id` INT NOT NULL AUTO_INCREMENT,
  `sum` DECIMAL(10,0) NOT NULL,
  `date` DATE NOT NULL,
  `organization` VARCHAR(45) NULL,
  `goal` VARCHAR(30) NULL,
  `bank_account_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`payment_id`),
  INDEX `fk1_Payments_BanckAccount_BanckAccountId_idx` (`bank_account_id` ASC) VISIBLE,
  INDEX `fk_payment_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk1_Payments_BanckAccount_BanckAccountId`
    FOREIGN KEY (`bank_account_id`)
    REFERENCES `banksystem`.`bank_account` (`bank_account_id`),
  CONSTRAINT `fk_payment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `banksystem`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;