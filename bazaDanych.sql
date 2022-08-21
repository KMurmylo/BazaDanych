CREATE TABLE klienci(
    id INT(6) AUTO_INCREMENT PRIMARY KEY,
	imie varchar(20) not null,
    nazwisko varchar(20)not null,
    numerKonta varchar(30) not null,
    stanKonta DECIMAL(10,2) not null,
    email varchar(20) null
);
ALTER TABLE klienci ADD CONSTRAINT U_klNK UNIQUE(numerKonta);
CREATE TABLE karty(
    id int(6) AUTO_INCREMENT PRIMARY KEY,
    wlasciciel INT(6) not null,
    numerKarty varchar(16) not null,
    pin varchar(4) not null,
    CONSTRAINT FOREIGN KEY (wlasciciel) REFERENCES klienci(id)
    );
CREATE TABLE platnoscKarta(
    id int(6) AUTO_INCREMENT PRIMARY KEY,
    idKarty int(6) not null,
    odbiorca varchar(30) not null,
    nazwa varchar(200) not null,
    dataWykonania date not null,
    ilosc DECIMAL(10,2) not null,
    CONSTRAINT FK_pKidKarty FOREIGN KEY (idKarty) REFERENCES karty(id),
    CONSTRAINT CHK_pKilosc CHECK(ilosc>0)
    )
    