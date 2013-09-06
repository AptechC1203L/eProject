use master
go

if db_id('MyProject') is not null
	drop database MyProject
go

create database MyProject
go

use MyProject
go

-- Bang danh muc SP
create table Category(
	CateID int primary key identity,
	CateName nvarchar(50),
	Description nvarchar(100)
)
go

-- them du lieu mau
insert into Category values('Sports', 'Xe the thao')
insert into Category values('Luxury', 'Xe hang sang')
insert into Category values('Family', 'Xe gia dinh')
go
