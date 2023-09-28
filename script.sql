create schema tortorici;
use tortorici;

create table utenti (
	id_utente int auto_increment primary key,
	tipo_utente smallint, 
		check (tipo_utente >= 0 and tipo_utente <= 1),					/*0 = DISTRIBUTORE, 1 = CLIENTE*/
	email varchar(64) not null unique,
    /*La password sarà di dimensione fissa poiché mappata nel DB con hash SHA-256.*/
	passw char(64) not null,
	stato boolean not null
);

create table clienti (
	ref_id_utente int primary key references utenti(id_utente),
    ref_email varchar(64) unique,
	nome varchar(64) not null,
    cognome varchar(64) not null,
    data_di_nascita date not null,
    saldo_disponibile decimal(10,2),
    foreign key(ref_email) references utenti(email) 
);

create table distributori (
	ref_id_utente int primary key references utenti(id_utente),
	posizione varchar(32) not null
);

create table prodotti (
	id_prodotto int primary key,
	nome_prodotto varchar(32),
    prezzo_prodotto decimal(3,2),
    tipo_prodotto smallint,
	check (tipo_prodotto >= 0 and tipo_prodotto <= 1) /* 0 = bevanda, 1 = cibo */
);

create table associazioni (
	id_associazione int auto_increment primary key,
	ref_id_distributore int references distributori(ref_id_utente),
	ref_id_cliente int references clienti(ref_id_utente),
    stato boolean not null
);

create table ordini (
	id_ordine int auto_increment primary key,
	data_ordine date,
	ref_email varchar(64) references clienti(ref_email),
	ref_id_distributore smallint references distributori(ref_id_utente),
    ref_id_prodotto smallint references prodotti(id_prodotto),
    importo decimal(3,2)
);

/* POPOLAZIONE DB */

insert into utenti values (1, 0, 'distributore01@unipa.it', '830e712d78587bea23d5dc35d5af9e986688ca2423aaaa7e4ddcb27442f2b4ee', true),
(2, 0, 'distributore02@unipa.it', '5b8435fc81c82428d7c73e0ec7a392fbf567513807228ecfaa252d5a77e3a77b', true),
(3, 0, 'distributore03@unipa.it', '345278aaf324b2ca7e82db907c7d59c18cf3a6f038d6f1b98ec2da6d25abb19c', true),
(4, 0, 'distributore04@unipa.it', 'd9d56b0976acd7f4fe3e7f4dbdf46ab496ca739298eed6befb640d62e89dc5c8', true),
(5, 1, 'mario.tortorici@unipa.it', '59195c6c541c8307f1da2d1e768d6f2280c984df217ad5f4c64c3542b04111a4', true);

insert into distributori values (1, 'Viale delle Scienze, Edificio 6'),
(2, 'Viale delle Scienze, Edificio 8'),
(3, 'Viale delle Scienze, Edificio 2'),
(4, 'Viale delle Scienze, Edificio 19');

insert into prodotti values (1, 'Caffè espresso corto', 0.50, 0),
(2, 'Caffè espresso lungo', 0.50, 0),
(3, 'Cappuccino', 0.60, 0),
(4, 'Cioccolata', 0.60, 0),
(5, 'KitKat', 1.00, 1),
(6, 'Mars', 1.00, 1),
(7, 'Oreo', 1.20, 1),
(8, 'Nutella Biscuits', 1.20, 1);

insert into clienti values (5, 'mario.tortorici@unipa.it', 'mario', 'tortorici', '2000-02-09', 0.00);