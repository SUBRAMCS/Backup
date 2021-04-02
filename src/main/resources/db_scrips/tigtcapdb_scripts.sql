USE [tightcapdb]
GO

/****** Object:  Table [dbo].[account_profile]    Script Date: 10/18/2019 7:53:04 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[account_profile](
	[Account_Profile_ID] [int] IDENTITY(1,1) NOT NULL,
	[Registration_ID] [int] NOT NULL,
	[Individual_ID] [int] NULL DEFAULT (NULL),
	[Address_ID] [int] NULL DEFAULT (NULL),
	[Document_ID] [int] NULL DEFAULT (NULL),
	[IsActive] [char](1) NULL DEFAULT ('Y'),
	[Record_Created_Date] [datetime] NULL DEFAULT (getdate()),
	[Record_Updated_Date] [datetime] NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[Account_Profile_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[account_profile]  WITH CHECK ADD FOREIGN KEY([Address_ID])
REFERENCES [dbo].[address_master] ([Address_ID])
GO

ALTER TABLE [dbo].[account_profile]  WITH CHECK ADD FOREIGN KEY([Address_ID])
REFERENCES [dbo].[address_master] ([Address_ID])
GO

ALTER TABLE [dbo].[account_profile]  WITH CHECK ADD FOREIGN KEY([Document_ID])
REFERENCES [dbo].[document_master] ([Document_ID])
GO

ALTER TABLE [dbo].[account_profile]  WITH CHECK ADD FOREIGN KEY([Document_ID])
REFERENCES [dbo].[document_master] ([Document_ID])
GO

ALTER TABLE [dbo].[account_profile]  WITH CHECK ADD FOREIGN KEY([Individual_ID])
REFERENCES [dbo].[Individual_Master] ([Individual_ID])
GO

ALTER TABLE [dbo].[account_profile]  WITH CHECK ADD FOREIGN KEY([Individual_ID])
REFERENCES [dbo].[Individual_Master] ([Individual_ID])
GO

ALTER TABLE [dbo].[account_profile]  WITH CHECK ADD FOREIGN KEY([Registration_ID])
REFERENCES [dbo].[Register_User] ([Registration_ID])
GO

ALTER TABLE [dbo].[account_profile]  WITH CHECK ADD FOREIGN KEY([Registration_ID])
REFERENCES [dbo].[Register_User] ([Registration_ID])
GO

---------------
USE [tightcapdb]
GO

/****** Object:  Table [dbo].[address_master]    Script Date: 10/18/2019 7:53:40 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[address_master](
	[Address_ID] [int] IDENTITY(1,1) NOT NULL,
	[Individual_ID] [int] NOT NULL,
	[Address_Line1] [varchar](50) NULL DEFAULT (NULL),
	[Address_Line2] [varchar](50) NULL DEFAULT (NULL),
	[Address_Line3] [varchar](50) NULL DEFAULT (NULL),
	[Address_Line4] [varchar](50) NULL DEFAULT (NULL),
	[County_State] [varchar](50) NULL DEFAULT (NULL),
	[PostCode] [varchar](10) NULL DEFAULT (NULL),
	[Country] [varchar](100) NOT NULL,
	[Sequence_Number] [int] NULL DEFAULT (NULL),
	[IsActive] [char](1) NULL DEFAULT ('Y'),
	[Record_Created_Date] [datetime] NULL DEFAULT (getdate()),
	[Record_Updated_Date] [datetime] NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[Address_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

----------
USE [tightcapdb]
GO

/****** Object:  Table [dbo].[Confirmation_Token]    Script Date: 10/18/2019 7:54:09 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Confirmation_Token](
	[Token_id] [int] IDENTITY(1,1) NOT NULL,
	[Registration_ID] [int] NULL,
	[User_login_ID] [varchar](50) NULL,
	[Confirmation_Token] [varchar](100) NOT NULL,
	[Confirmation_Token_Link] [varchar](500) NULL,
	[Rem_Token_Create_DateTime] [datetime] NULL,
	[Rem_Token_Expary_DateTime] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Token_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[Confirmation_Token] ADD  DEFAULT (getdate()) FOR [Rem_Token_Create_DateTime]
GO

ALTER TABLE [dbo].[Confirmation_Token]  WITH CHECK ADD FOREIGN KEY([Registration_ID])
REFERENCES [dbo].[Register_User] ([Registration_ID])
GO

-------
USE [tightcapdb]
GO

/****** Object:  Table [dbo].[document_master]    Script Date: 10/18/2019 7:54:30 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[document_master](
	[Document_ID] [int] IDENTITY(1,1) NOT NULL,
	[Address_ID] [int] NOT NULL,
	[Individual_ID] [int] NOT NULL,
	[Document_Type] [varchar](40) NULL,
	[Country_of_Issue] [varchar](100) NULL,
	[Document_Number] [varchar](40) NULL,
	[Issueing_Authority] [varchar](400) NULL,
	[Issue_Date] [date] NULL,
	[Expiry_Date] [date] NULL,
	[Additional_Information] [varchar](2000) NULL,
	[Document_Identifier] [varchar](300) NOT NULL,
	[Document_Path] [varchar](1500) NOT NULL,
	[Sequence_Number] [int] NOT NULL,
	[IsActive] [char](1) NULL,
	[Record_Created_Date] [datetime] NULL,
	[Record_Updated_Date] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[Document_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[document_master] ADD  DEFAULT (NULL) FOR [Document_Type]
GO

ALTER TABLE [dbo].[document_master] ADD  DEFAULT (NULL) FOR [Country_of_Issue]
GO

ALTER TABLE [dbo].[document_master] ADD  DEFAULT (NULL) FOR [Document_Number]
GO

ALTER TABLE [dbo].[document_master] ADD  DEFAULT (NULL) FOR [Issueing_Authority]
GO

ALTER TABLE [dbo].[document_master] ADD  DEFAULT (NULL) FOR [Issue_Date]
GO

ALTER TABLE [dbo].[document_master] ADD  DEFAULT (NULL) FOR [Expiry_Date]
GO

ALTER TABLE [dbo].[document_master] ADD  DEFAULT (NULL) FOR [Additional_Information]
GO

ALTER TABLE [dbo].[document_master] ADD  DEFAULT ('Y') FOR [IsActive]
GO

ALTER TABLE [dbo].[document_master] ADD  DEFAULT (getdate()) FOR [Record_Created_Date]
GO

ALTER TABLE [dbo].[document_master] ADD  DEFAULT (getdate()) FOR [Record_Updated_Date]
GO

-------
USE [tightcapdb]
GO

/****** Object:  Table [dbo].[Email_Master]    Script Date: 10/18/2019 7:54:50 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Email_Master](
	[Email_ID] [int] IDENTITY(1,1) NOT NULL,
	[Individual_ID] [int] NULL,
	[Email_Address] [varchar](80) NOT NULL,
	[IsPrimary] [char](1) NULL,
	[Sequence_Number] [int] NULL,
	[IsActive] [char](1) NULL DEFAULT ('N'),
	[Record_Created_Date] [datetime] NULL DEFAULT (getdate()),
	[Record_Updated_Date] [datetime] NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[Email_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[Email_Master]  WITH CHECK ADD FOREIGN KEY([Individual_ID])
REFERENCES [dbo].[Individual_Master] ([Individual_ID])
GO

------------
USE [tightcapdb]
GO

/****** Object:  Table [dbo].[Individual_Master]    Script Date: 10/18/2019 7:55:12 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Individual_Master](
	[Individual_ID] [int] IDENTITY(1,1) NOT NULL,
	[Registration_ID] [int] NULL,
	[Title] [varchar](10) NULL DEFAULT ('elocker'),
	[First_Name] [varchar](50) NOT NULL,
	[Middle_Name] [varchar](50) NULL,
	[Mother_Maiden_Name] [varchar](45) NOT NULL,
	[Last_Name] [varchar](50) NULL,
	[DOB] [date] NOT NULL,
	[Gender] [char](1) NULL,
	[IsActive] [char](1) NULL DEFAULT ('N'),
	[Record_Created_Date] [datetime] NULL DEFAULT (getdate()),
	[Record_Updated_Date] [datetime] NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[Individual_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[Individual_Master]  WITH CHECK ADD FOREIGN KEY([Registration_ID])
REFERENCES [dbo].[Register_User] ([Registration_ID])
GO

----------
USE [tightcapdb]
GO

/****** Object:  Table [dbo].[Phone_Master]    Script Date: 10/18/2019 7:55:29 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Phone_Master](
	[Phone_ID] [int] IDENTITY(1,1) NOT NULL,
	[Individual_ID] [int] NULL,
	[Country_Code] [varchar](5) NULL DEFAULT ('GB'),
	[Phone_Number] [varchar](15) NOT NULL,
	[isPrimary] [char](1) NULL,
	[Sequence_Number] [int] NULL,
	[IsActive] [char](1) NULL DEFAULT ('N'),
	[Record_Created_Date] [datetime] NULL DEFAULT (getdate()),
	[Record_Updated_Date] [datetime] NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[Phone_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[Phone_Master]  WITH CHECK ADD FOREIGN KEY([Individual_ID])
REFERENCES [dbo].[Individual_Master] ([Individual_ID])
GO


----------------
USE [tightcapdb]
GO

/****** Object:  Table [dbo].[Register_User]    Script Date: 10/18/2019 7:55:53 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Register_User](
	[Registration_ID] [int] IDENTITY(1,1) NOT NULL,
	[User_login_ID] [varchar](50) NOT NULL,
	[User_password] [varchar](500) NOT NULL,
	[Password_Hashing_Algorithim] [varchar](100) NULL DEFAULT ('SHA-512'),
	[DOB] [date] NOT NULL,
	[Question] [varchar](500) NULL,
	[Question_Answer] [varchar](100) NULL,
	[IsActive] [char](1) NULL DEFAULT ('N'),
	[AccountLocked] [char](1) NULL DEFAULT ('N'),
	[LockedExpiryDate] [datetime] NULL DEFAULT (getdate()),
	[Accept_Term_of_Service] [char](1) NULL DEFAULT ('Y'),
	[Account_Closed_DateTime] [datetime] NULL DEFAULT ('9999-12-31 23:59:59'),
	[Password_Updated_DateTime] [datetime] NULL DEFAULT (getdate()),
	[Record_Created_Date] [datetime] NULL DEFAULT (getdate()),
	[Record_Updated_Date] [datetime] NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[Registration_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

-------
USE [tightcapdb]
GO

/****** Object:  Table [dbo].[reset_pwd_token]    Script Date: 10/18/2019 7:56:11 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[reset_pwd_token](
	[Token_id] [int] IDENTITY(1,1) NOT NULL,
	[Registration_ID] [int] NOT NULL,
	[User_login_ID] [varchar](50) NOT NULL,
	[reset_Token] [varchar](100) NOT NULL,
	[reset_Token_Link] [varchar](500) NOT NULL,
	[reset_Token_Create_DateTime] [datetime] NOT NULL,
	[reset_Token_Expary_DateTime] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Token_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[reset_pwd_token] ADD  DEFAULT (getdate()) FOR [reset_Token_Create_DateTime]
GO

-----------
USE [tightcapdb]
GO

/****** Object:  Table [dbo].[User_Attempts]    Script Date: 10/18/2019 7:56:29 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[User_Attempts](
	[id] [int] NULL,
	[username] [varchar](50) NOT NULL,
	[attempts] [int] NULL DEFAULT ((0)),
	[lastModified] [datetime] NULL DEFAULT (getdate())
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[User_Attempts]  WITH CHECK ADD FOREIGN KEY([id])
REFERENCES [dbo].[Register_User] ([Registration_ID])
GO

-------------------
ALTER TABLE [dbo].[Phone_Master] ADD Country_Id int;

ALTER TABLE [elocker].[Phone_Master]  WITH CHECK ADD FOREIGN KEY([Country_Id])
REFERENCES [elocker].[country_code] ([Country_Id])

CREATE TABLE [dbo].[country_code] (
	[Country_Id] [int] IDENTITY(1, 1) NOT NULL
	,[country_name] [varchar](50) NOT NULL
	,[top_level_domain] [char](2) NULL
	,[country_ph_code] [int] NOT NULL
	,[continent] [varchar](50) NOT NULL
	,[capital] [varchar](50) NULL
	,[time_zone_in_capital] [varchar](100) NOT NULL
	,[currency] [varchar](50) NULL
	,PRIMARY KEY CLUSTERED ([Country_Id] ASC) WITH (
		PAD_INDEX = OFF
		,STATISTICS_NORECOMPUTE = OFF
		,IGNORE_DUP_KEY = OFF
		,ALLOW_ROW_LOCKS = ON
		,ALLOW_PAGE_LOCKS = ON
		,OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF
		) ON [PRIMARY]
	) ON [PRIMARY];


CREATE TABLE [dbo].[family_member] (
	[family_member_id] [int] IDENTITY(1, 1) NOT NULL
	,[account_profile_id] [int] NULL
	,[individual_id] [int] NULL
	,[address_id] [int] NULL
	,[document_id] [int] NULL
	,[relationship] [varchar](50) NOT NULL
	,[additional_info] [varchar](2000) NULL
	,[isActive] [char](1) NULL
	,[Record_Created_DateTime] [datetime] NULL
	,[Record_Updated_DateTime] [datetime] NULL
	) ON [PRIMARY];

CREATE TABLE [dbo].[nominee_member] (
	[nominee_member_id] [int] IDENTITY(1, 1) NOT NULL
	,[account_profile_id] [int] NULL
	,[individual_id] [int] NULL
	,[address_id] [int] NULL
	,[document_id] [int] NULL
	,[relationship] [varchar](50) NOT NULL
	,[additional_info] [varchar](2000) NULL
	,[isActive] [char](1) NULL
	,[Record_Created_DateTime] [datetime] NULL
	,[Record_Updated_DateTime] [datetime] NULL
	,[primary_nominee] [char](1) NOT NULL
	) ON [PRIMARY];




