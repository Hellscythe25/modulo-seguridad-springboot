
CREATE TABLE CLIENTS
(	id					int		 	 not null primary key,
	name				varchar(50)  not null,
	lastname			varchar(50)  not null,
	sex					char(1)		 not null,
	address				varchar(100) not null,
	dni					char(8)		 not null,
	celphone			varchar(9)   not null,
	username 			varchar(20)  not null,
	pass 				varchar(100) not null,
	birthday			datetime
)
/*
alter table CLIENTS
add constraint  CHK_dni check (dni like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')

alter table CLIENTS
add constraint CHK_celphone check (celphone like '[9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')

alter table CLIENT drop check CHK_dni
*/

alter table CLIENTS
add constraint CHK_sex check (sex in ('F','M'))

alter table CLIENTS
add unique (username);

alter table CLIENTS
add unique (celphone);

alter table CLIENTS
add unique (dni);

alter table CLIENTS modify column id int auto_increment not null;

insert into CLIENTS(name, lastname, sex, address, dni, celphone, username, pass, birthday)
values('Mike', 'Monty', 'M', 'Chaclacayo', '46464646', '999999999', 'Mmonty','123456' , '19850416');

insert into CLIENTS(name, lastname, sex, address, dni, celphone, username, pass, birthday)
values('Bob', 'Moarley', 'M', 'Jamaica', '42042042', '988888888', 'RastaKing','420' , '19450206');

insert into CLIENTS(name, lastname, sex, address, dni, celphone, username, pass, birthday)
values('Lucero', 'Williams', 'F', 'USA', '55555555', '938888888', 'LuWilliams','111111' , '19990506');

/*drop table CLIENTS*/
select * from CLIENTS
show tables

create procedure insertClients(in p_name varchar(50),
						   	   in p_lastname varchar(50),
						  	   in p_sex char(1),
						   	   in p_address varchar(100),
						   	   in p_dni char(8),
						   	   in p_celphone varchar(9),
						   	   in p_username varchar(20),
						   	   in p_pass varchar(100),
						   	   in p_birthday datetime)
begin
	declare countClients int default 0;
	select count(*) into countClients from CLIENTS where username=p_username;
	if(countClients>0) then
		select 'Client already exists';
	else
		insert into CLIENTS(name, lastname, sex, address, dni, celphone, username, pass, birthday)
		values(p_name, p_lastname, p_sex, p_address, p_dni, p_celphone, p_username, p_pass, p_birthday);
	end if;

end



create procedure getAllClients()
begin
	select * from CLIENTS;
end

create procedure deleteClientById(in p_id int)
begin
	declare countClients int default 0;
	select count(*) into countClients from CLIENTS where id=p_id;
	if(countClients <=0) then
		select 'User already exists';
	else
		delete from CLIENTS where id=p_id;
	end if;
end

create procedure login(in p_username varchar(20), in p_pass varchar(100))
begin
	declare countFind INT default 0;
	declare countValidate int default 0;

	select count(*) into countFind from CLIENTS where username = p_username;

	if (countFind <= 0) then
		select 'User does not exist';
	else
		select count(*) into countValidate from CLIENTS where username = p_username and pass = p_pass;

		if(countValidate >	0) then
			select concat('WELCOME ', name) from  CLIENTS where username = p_username;
		else
			select 'User or password invalid';
		end if;
	end if;
end

create procedure updatePasswordByUsername(in p_username varchar(20), in p_pass varchar(20), out out_AffectedRows INT)
begin
	update CLIENTS set pass = p_pass where username = p_username;
	select ROW_COUNT() into out_AffectedRows;
end

call updatePasswordByUsername('Mmonty', '222222', @outvar);
select @outvar

call login('RastaKing', '420');
call deleteClientById(4);
call getAllClients();
call insertClients('Erika', 'Velarde', 'F', 'Lima', '44444444', '945498758', 'EriVe', '666', '19920103');

/*drop procedure updatePasswordByUsername*/