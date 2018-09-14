/*
//
//DATABASE CREATION
//
*/
--create database PFA
CREATE DATABASE PFA
ENCODING='WIN1252'
template=template0
CONNECTION LIMIT -1;

/*
//
//TABLES CREATION
//
*/
--create table usuario
CREATE TABLE IF NOT EXISTS usuario(
	id_usuario SERIAL CONSTRAINT nn_id_usuario NOT NULL,
	nome_usuario VARCHAR(70) CONSTRAINT nn_nome_usuario NOT NULL,
	rua_endereco VARCHAR(50) CONSTRAINT nn_rua_endereco NOT NULL,
	numero_endereco INTEGER CONSTRAINT nn_numero_endereco NOT NULL,
	cep_endereco VARCHAR(10) CONSTRAINT nn_cep_endereco NOT NULL,
	bairro_endereco VARCHAR(50) CONSTRAINT nn_bairro_endereco NOT NULL,
	complemento_endereco VARCHAR(30),
	cpf_usuario VARCHAR(14) CONSTRAINT nn_cpf_usuario NOT NULL,
	email_usuario VARCHAR(50) CONSTRAINT nn_email_usuario NOT NULL,
	senha_usuario VARCHAR(10) CONSTRAINT nn_senha_usuario NOT NULL,
	dia_util_salario INTEGER CONSTRAINT nn_dia_util_salario NOT NULL,
	saldo_usuario NUMERIC(7,2) CONSTRAINT nn_saldo_usuario NOT NULL DEFAULT 0.0,
	data_cadastro_usuario TIMESTAMP CONSTRAINT nn_data_cadastro_usuario NOT NULL DEFAULT now(), 
	CONSTRAINT pk_id_usuario PRIMARY KEY (id_usuario)	
);

--create table tipo_despesa
CREATE TABLE IF NOT EXISTS tipo_despesa(
	id_tipo_despesa SERIAL CONSTRAINT nn_id_tipo_despesa NOT NULL, 
	nome_tipo_despesa VARCHAR(50) CONSTRAINT nn_nome_tipo_despesa NOT NULL,
	CONSTRAINT pk_id_tipo_despesa PRIMARY KEY (id_tipo_despesa)
);

--create table despesa
CREATE TABLE IF NOT EXISTS despesa(
	id_despesa SERIAL CONSTRAINT nn_id_despesa NOT NULL,
	id_tipo_despesa INTEGER CONSTRAINT nn_tipo_despesa NOT NULL,
	id_usuario INTEGER CONSTRAINT nn_usuario NOT NULL,
	data_despesa DATE CONSTRAINT nn_data_despesa NOT NULL DEFAULT current_date,
	valor_despesa NUMERIC(7,2) CONSTRAINT nn_valor_despesa NOT NULL,	
	descricao_despesa VARCHAR(255) DEFAULT NULL,
	CONSTRAINT pk_id_despesa PRIMARY KEY (id_despesa),
	CONSTRAINT fk_despesa_tipo_despesa FOREIGN KEY (id_tipo_despesa) REFERENCES tipo_despesa (id_tipo_despesa) ON DELETE RESTRICT,
	CONSTRAINT fk_despesa_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE RESTRICT
);

--create table tipo_receita
CREATE TABLE IF NOT EXISTS tipo_receita(
	id_tipo_receita SERIAL CONSTRAINT nn_id_tipo_receita NOT NULL, 
	nome_tipo_receita VARCHAR(50) CONSTRAINT nn_nome_tipo_receita NOT NULL,
	CONSTRAINT pk_id_tipo_receita PRIMARY KEY (id_tipo_receita)	
);

--create table receita
CREATE TABLE IF NOT EXISTS receita(
	id_receita SERIAL CONSTRAINT nn_id_receita NOT NULL,
	id_tipo_receita INTEGER CONSTRAINT nn_tipo_receita NOT NULL,
	id_usuario INTEGER CONSTRAINT nn_usuario NOT NULL,
	data_receita DATE CONSTRAINT nn_data_receita NOT NULL DEFAULT current_date,
	valor_receita NUMERIC(7,2) CONSTRAINT nn_valor_receita NOT NULL,
	descricao_receita VARCHAR(255) DEFAULT NULL,
	CONSTRAINT pk_id_receita PRIMARY KEY (id_receita),
	CONSTRAINT fk_receita_tipo_receita FOREIGN KEY (id_tipo_receita) REFERENCES tipo_receita(id_tipo_receita) ON DELETE RESTRICT,
	CONSTRAINT fk_receita_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE RESTRICT	
);

/*
//
//TRIGGERS AND FUNCTIONS TRIGGER FOR despesa
//BEGIN
*/
--//INSERT
--trigger function for decrease saldo_usuario after insert despesa
CREATE OR REPLACE FUNCTION pr_after_insert_despesa() 
RETURNS trigger AS 
$$
BEGIN
	--check new value of valor_despesa
	IF NEW.valor_despesa > 0 THEN
		UPDATE usuario SET saldo_usuario=(saldo_usuario-NEW.valor_despesa) WHERE id_usuario=new.id_usuario;	
	END IF;
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

--trigger AFTER INSERT ON despesa
CREATE TRIGGER tr_after_insert_despesa
	AFTER INSERT ON despesa
	FOR EACH ROW
	EXECUTE PROCEDURE pr_after_insert_despesa();

--//UPDATE
--trigger function for decrease saldo_usuario after update despesa
CREATE OR REPLACE FUNCTION pr_after_update_despesa() 
RETURNS trigger AS 
$$
BEGIN
	--check new value and old value of valor_despesa
	IF OLD.valor_despesa > NEW.valor_despesa THEN
		UPDATE usuario SET saldo_usuario=(saldo_usuario+(OLD.valor_despesa-NEW.valor_despesa)) WHERE id_usuario=OLD.id_usuario;
	ELSEIF OLD.valor_despesa < NEW.valor_despesa THEN
		UPDATE usuario SET saldo_usuario=((saldo_usuario+OLD.valor_despesa)-NEW.valor_despesa) WHERE id_usuario=OLD.id_usuario;
	END IF;
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';
--trigger AFTER UPDATE ON despesa
CREATE TRIGGER tr_after_update_despesa
	AFTER UPDATE ON despesa
	FOR EACH ROW
	EXECUTE PROCEDURE pr_after_update_despesa();	
--//DELETE
--trigger function for decrease saldo_usuario after delete despesa
CREATE OR REPLACE FUNCTION pr_after_delete_despesa() 
RETURNS trigger AS 
$$
BEGIN	
	--increase saldo_usuario because we'll deleting despesa
	UPDATE usuario SET saldo_usuario=(saldo_usuario+OLD.valor_despesa) WHERE id_usuario=OLD.id_usuario;	
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';
--trigger DELETE INSERT ON despesa
CREATE TRIGGER tr_after_delete_despesa
	AFTER DELETE ON despesa
	FOR EACH ROW
	EXECUTE PROCEDURE pr_after_delete_despesa();
/*
//
//TRIGGERS AND FUNCTIONS TRIGGER FOR despesa
//END
*/	

/*
//
//TRIGGERS AND FUNCTIONS TRIGGER FOR receita
//BEGIN
*/
--//INSERT
--trigger function for decrease saldo_usuario after insert receita
CREATE OR REPLACE FUNCTION pr_after_insert_receita() 
RETURNS trigger AS 
$$
BEGIN
	--check new value of valor_receita
	IF NEW.valor_receita > 0 THEN
		UPDATE usuario SET saldo_usuario=(saldo_usuario+NEW.valor_receita) WHERE id_usuario=NEW.id_usuario;	
	END IF;
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

--trigger AFTER INSERT ON receita
CREATE TRIGGER tr_after_insert_receita
	AFTER INSERT ON receita
	FOR EACH ROW
	EXECUTE PROCEDURE pr_after_insert_receita();
--//UPDATE
--trigger function for decrease saldo_usuario after update receita
CREATE OR REPLACE FUNCTION pr_after_update_receita() 
RETURNS trigger AS 
$$
BEGIN
	--check new value and old value of valor_receita
	IF OLD.valor_receita > NEW.valor_receita THEN
		UPDATE usuario SET saldo_usuario=(saldo_usuario-(OLD.valor_receita-NEW.valor_receita)) WHERE id_usuario=OLD.id_usuario;
	ELSEIF OLD.valor_receita < NEW.valor_receita THEN
		UPDATE usuario SET saldo_usuario=((saldo_usuario+NEW.valor_receita)-OLD.valor_receita) WHERE id_usuario=OLD.id_usuario;
	END IF;
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';
--trigger AFTER UPDATE ON receita
CREATE TRIGGER tr_after_update_receita
	AFTER UPDATE ON receita
	FOR EACH ROW
	EXECUTE PROCEDURE pr_after_update_receita();	
--//DELETE
--trigger function for decrease saldo_usuario after delete receita
CREATE OR REPLACE FUNCTION pr_after_delete_receita() 
RETURNS trigger AS 
$$
BEGIN	
	--increase saldo_usuario because we'll deleting receita
	UPDATE usuario SET saldo_usuario=(saldo_usuario-OLD.valor_receita) WHERE id_usuario=OLD.id_usuario;	
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';
--trigger DELETE INSERT ON receita
CREATE TRIGGER tr_after_delete_receita
	AFTER DELETE ON receita
	FOR EACH ROW
	EXECUTE PROCEDURE pr_after_delete_receita();	
/*
//
//TRIGGERS AND FUNCTIONS TRIGGER FOR receita
//END
*/

--test trigger using random data
INSERT INTO usuario (nome_usuario, 
		     rua_endereco, numero_endereco, cep_endereco, bairro_endereco, 
		     cpf_usuario, email_usuario, senha_usuario, dia_util_salario, saldo_usuario)
		     VALUES
		     ('Leonardo', 'Rua Test', 683, '14600-000', 'Baixada', '418.176.456-58', 'leonardo@test.com', 'test123', 4, 1500.00);
--despesa
insert into tipo_despesa (nome_tipo_despesa) VALUES ('Transporte');
insert into despesa (id_tipo_despesa, id_usuario, data_despesa, valor_despesa) VALUES (1, 1, '2018-09-01', 500.00);
update despesa set valor_despesa=550 where id_despesa=1;
delete from despesa where id_despesa=1;	    
select * from despesa inner join tipo_despesa on despesa.id_tipo_despesa=tipo_despesa.id_tipo_despesa;
select * from usuario;	
--receita
insert into tipo_receita (nome_tipo_receita) VALUES ('Salario');
insert into receita (id_tipo_receita, id_usuario, data_receita, valor_receita) VALUES (1, 1, '2018-09-01', 500.00);
update receita set valor_receita=550 where id_receita=1;
delete from receita where id_receita=1;	    
select * from receita inner join tipo_receita on receita.id_tipo_receita=tipo_receita.id_tipo_receita;
select * from usuario;	








