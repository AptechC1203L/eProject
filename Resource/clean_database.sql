CREATE TABLE [Roles] (
  [name] varchar(20) NOT NULL , 
 PRIMARY KEY ([name])
) ON [PRIMARY]
GO

CREATE TABLE [Orders] (
  [id] int NOT NULL IDENTITY (1, 1) ,
  [from] nvarchar(500) ,
  [to] nvarchar(500) ,
  [creator_id] varchar(20) ,
  [deliverer_id] varchar(20) ,
  [status] varchar(20) ,
  [weight] float ,
  [description] ntext ,
  [timestamp] datetime NOT NULL ,
  [due_date] datetime ,
  [delivered_date] datetime , 
 PRIMARY KEY ([id])
) ON [PRIMARY]
GO

CREATE TABLE [Users] (
  [username] varchar(20) NOT NULL ,
  [name] nvarchar(100) ,
  [honorific] nvarchar(100) ,
  [about_me] ntext ,
  [phone] varchar(100) , 
 PRIMARY KEY ([username])
) ON [PRIMARY]
GO

CREATE TABLE [UserRole] (
  [id] int NOT NULL IDENTITY (1, 1) ,
  [username] varchar(20) NOT NULL ,
  [role] varchar(20) NOT NULL , 
 PRIMARY KEY ([id])
) ON [PRIMARY]
GO

CREATE TABLE [Permissions] (
  [id] int NOT NULL IDENTITY (1, 1) ,
  [action] varchar(20) ,
  [target] varchar(200) , 
 PRIMARY KEY ([id])
) ON [PRIMARY]
GO

CREATE TABLE [RolePermission] (
  [id] int NOT NULL IDENTITY (1, 1) ,
  [role_name] varchar(20) NOT NULL ,
  [permission_id] int NOT NULL , 
 PRIMARY KEY ([id])
) ON [PRIMARY]
GO

CREATE TABLE [Prices] (
  [established_date] datetime NOT NULL ,
  [price_per_kilogram] money NOT NULL , 
 PRIMARY KEY ([established_date])
) ON [PRIMARY]
GO

CREATE TABLE [Log] (
  [timestamp] datetime NOT NULL ,
  [content] ntext , 
 PRIMARY KEY ([timestamp])
) ON [PRIMARY]
GO

CREATE TABLE [WorkJournal] (
  [date] datetime NOT NULL ,
  [username] varchar(20) ,
  [presence] varchar(20) ,
  [reason] ntext , 
 PRIMARY KEY ([date])
) ON [PRIMARY]
GO

CREATE TABLE [PresenceStatuses] (
  [presence] varchar(20) NOT NULL , 
 PRIMARY KEY ([presence])
) ON [PRIMARY]
GO

CREATE TABLE [OrderStatuses] (
  [status] varchar(20) NOT NULL , 
 PRIMARY KEY ([status])
) ON [PRIMARY]
GO

ALTER TABLE [Orders] ADD FOREIGN KEY (creator_id) REFERENCES [Users] ([username]);
				
ALTER TABLE [Orders] ADD FOREIGN KEY (deliverer_id) REFERENCES [Users] ([username]);
				
ALTER TABLE [Orders] ADD FOREIGN KEY (status) REFERENCES [OrderStatuses] ([status]);
				
ALTER TABLE [UserRole] ADD FOREIGN KEY (username) REFERENCES [Users] ([username]);
				
ALTER TABLE [UserRole] ADD FOREIGN KEY (role) REFERENCES [Roles] ([name]);
				
ALTER TABLE [RolePermission] ADD FOREIGN KEY (role_name) REFERENCES [Roles] ([name]);
				
ALTER TABLE [RolePermission] ADD FOREIGN KEY (permission_id) REFERENCES [Permissions] ([id]);
				
ALTER TABLE [WorkJournal] ADD FOREIGN KEY (username) REFERENCES [Users] ([username]);
				
ALTER TABLE [WorkJournal] ADD FOREIGN KEY (presence) REFERENCES [PresenceStatuses] ([presence]);
		
		
-- Custom data
				
INSERT INTO [Users] VALUES ('admin', 'Administrator', 'Mr.', 'I''m the admin', '1234')
INSERT INTO [Users] VALUES ('receptionist', 'Receptionist', 'Mrs.', 'I''m the receptionist', '1234')
INSERT INTO [Users] VALUES ('deliverer', 'Deliverer', 'Mr.', 'I''m the deliverer', '1234')

INSERT INTO [OrderStatuses] VALUES ('DELIVERED')
INSERT INTO [OrderStatuses] VALUES ('REJECTED')
INSERT INTO [OrderStatuses] VALUES ('PENDING')
INSERT INTO [OrderStatuses] VALUES ('CONFIRMED')
INSERT INTO [OrderStatuses] VALUES ('DELIVERING')

INSERT INTO [PresenceStatuses] VALUES ('PRESENT')
INSERT INTO [PresenceStatuses] VALUES ('ABSENT')
INSERT INTO [PresenceStatuses] VALUES ('ONLEAVE')
