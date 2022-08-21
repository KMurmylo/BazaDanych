CREATE PROCEDURE CreateTransaction(
    IN InumerKarty varchar(30),IN Ipin varchar(4),IN InazwaPrzelewu varchar(200),Iilosc decimal(10,2),IN Iodbiorca varchar(30),
    OUT result varchar(50)
    )

body: BEGIN

	DECLARE idKarty int;
     DECLARE odbiorcaId int;
     DECLARE balans decimal(10,2);
    DECLARE klientId int;
   
   	select id into @idKarty from karty where numerKarty=InumerKarty and pin=Ipin;
    if(isnull(@idKarty))
       THEN
       	SET result="Incorrect payment information";
       	LEAVE body;
    end if;
    
    
    select kl.id into @klientId from klienci kl inner join karty ka on ka.wlasciciel=kl.id where ka.id=@idKarty group by 		kl.id;   
    select stanKonta into @balans from klienci where id=@klientId;
    if (@balans-Iilosc)<0
    	THEN
        	SET result="Not enough money in the account";
            LEAVE body;
    end if;
    
    
   
    SELECT id into @odbiorcaId from klienci where numerKonta=Iodbiorca;
    if(isnull(odbiorcaId))
       THEN	
      		START TRANSACTION;
       				update klienci set stanKonta=stanKonta-Iilosc where id=@klientId;
       				INSERT INTO `platnosckarta`(`id`, `idKarty`, `odbiorca`, `nazwa`, `dataWykonania`, `ilosc`) VALUES 
       						(null,@idKarty,Iodbiorca,InazwaPrzelewu,CURRENT_DATE(),Iilosc);
       		COMMIT;
       ELSE
       		START TRANSACTION;
       				update klienci set stanKonta=stanKonta-Iilosc where id=@klientId;
       				update klienci set stanKonta=stanKonta+Iilosc where id=@odbiorcaId;
       				INSERT INTO `platnosckarta`(`id`, `idKarty`, `odbiorca`, `nazwa`, `dataWykonania`, `ilosc`) VALUES 
       					(null,@idKarty,Iodbiorca,InazwaPrzelewu,CURRENT_DATE(),Iilosc);
       		COMMIT;
       END IF;
       
    
    SET result = "Transaction completed";
    

END