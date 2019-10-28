insert into products(name)
values('Dress');
set @id = LAST_INSERT_ID();

insert into products(name)
values('Hoodie');
set @id = LAST_INSERT_ID();

insert into categories(name)
values('Women');
set @id = LAST_INSERT_ID();

insert into categories(name)
values('Men');
set @id = LAST_INSERT_ID();


-- insert into product_categories
-- values(1,1);