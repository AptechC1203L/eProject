


CREATE TABLE [Roles] (
  [name] varchar(20) NOT NULL , 
 PRIMARY KEY ([name])
) ON [PRIMARY]
GO

CREATE TABLE [Orders] (
  [id] int IDENTITY (1, 1) ,
  [from] nvarchar(500) NOT NULL ,
  [to] nvarchar(500) NOT NULL ,
  [creator_id] varchar(20) ,
  [deliverer_id] varchar(20) ,
  [status] varchar(20) ,
  [weight] float NOT NULL ,
  [description] ntext ,
  [timestamp] timestamp NOT NULL ,
  [due_data] datetime NOT NULL , 
 PRIMARY KEY ([id])
) ON [PRIMARY]
GO

CREATE TABLE [Users] (
  [username] varchar(20) NOT NULL ,
  [name] nvarchar(100) NOT NULL ,
  [honorific] nvarchar(100) ,
  [about_me] ntext ,
  [phone] varchar(100) , 
 PRIMARY KEY ([username])
) ON [PRIMARY]
GO

CREATE TABLE [UserRoleMapping] (
  [username] varchar(20) NOT NULL ,
  [role] varchar(20) NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [Permissions] (
  [id] int IDENTITY (1, 1) ,
  [action] varchar(20) NOT NULL ,
  [target] varchar(200) NOT NULL , 
 PRIMARY KEY ([id])
) ON [PRIMARY]
GO

CREATE TABLE [RolePermissionMapping] (
  [role_name] varchar(20) NOT NULL ,
  [permission_id] int NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [Prices] (
  [established_date] datetime NOT NULL ,
  [price_per_kilogram] money NOT NULL , 
 PRIMARY KEY ([established_date])
) ON [PRIMARY]
GO

CREATE TABLE [Log] (
  [timestamp] timestamp NOT NULL ,
  [content] ntext , 
 PRIMARY KEY ([timestamp])
) ON [PRIMARY]
GO

CREATE TABLE [WorkJournal] (
  [date] datetime ,
  [username] varchar(20) NOT NULL ,
  [status] varchar(20) NOT NULL ,
  [reason] ntext , 
 PRIMARY KEY ([date])
) ON [PRIMARY]
GO

ALTER TABLE [Orders] ADD FOREIGN KEY (creator_id) REFERENCES [Users] ([username]);
				
ALTER TABLE [Orders] ADD FOREIGN KEY (deliverer_id) REFERENCES [Users] ([username]);
				
ALTER TABLE [UserRoleMapping] ADD FOREIGN KEY (username) REFERENCES [Users] ([username]);
				
ALTER TABLE [UserRoleMapping] ADD FOREIGN KEY (role) REFERENCES [Roles] ([name]);
				
ALTER TABLE [RolePermissionMapping] ADD FOREIGN KEY (role_name) REFERENCES [Roles] ([name]);
				
ALTER TABLE [RolePermissionMapping] ADD FOREIGN KEY (permission_id) REFERENCES [Permissions] ([id]);
				
ALTER TABLE [WorkJournal] ADD FOREIGN KEY (username) REFERENCES [Users] ([username]);
				

