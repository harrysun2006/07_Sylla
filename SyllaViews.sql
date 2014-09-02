USE [Sylla]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- 删除视图： CORP_沧浪，CORP_相城，CORP_新区，CORP_园区，CORP_金阊，CORP_吴中，CORP_平江
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_沧浪]'))
DROP VIEW [dbo].[CORP_沧浪]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_相城]'))
DROP VIEW [dbo].[CORP_相城]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_新区]'))
DROP VIEW [dbo].[CORP_新区]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_园区]'))
DROP VIEW [dbo].[CORP_园区]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_金阊]'))
DROP VIEW [dbo].[CORP_金阊]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_吴中]'))
DROP VIEW [dbo].[CORP_吴中]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_平江]'))
DROP VIEW [dbo].[CORP_平江]
GO

-- 重建视图： CORP_沧浪，CORP_相城，CORP_新区，CORP_园区，CORP_金阊，CORP_吴中，CORP_平江
-- 苏州7个区
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_沧浪]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP_沧浪]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND (CORP_Addr LIKE N''%沧浪%'' OR CORP_Name2 LIKE N''%沧浪%'' OR CORP_Precinct LIKE N''%沧浪%'' OR CORP_RegOrgan LIKE N''%沧浪%'')
'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_相城]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP_相城]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND (CORP_Addr LIKE N''%相城%'' OR CORP_Name2 LIKE N''%相城%'' OR CORP_Precinct LIKE N''%相城%'' OR CORP_RegOrgan LIKE N''%相城%'')
'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_新区]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP_新区]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND (CORP_Addr LIKE N''%新区%'' OR CORP_Name2 LIKE N''%新区%'' OR CORP_Precinct LIKE N''%新区%'' OR CORP_RegOrgan LIKE N''%新区%'')
'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_园区]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP_园区]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND ((CORP_Addr LIKE N''%园区%'') OR (CORP_Name2 LIKE N''%园区%'') OR (CORP_Precinct LIKE N''%园区%'') OR (CORP_RegOrgan LIKE N''%园区%''))
'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_金阊]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP_金阊]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND (CORP_Addr LIKE N''%金阊%'' OR CORP_Name2 LIKE N''%金阊%'' OR CORP_Precinct LIKE N''%金阊%'' OR CORP_RegOrgan LIKE N''%金阊%'')
'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_吴中]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP_吴中]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND (CORP_Addr LIKE N''%吴中%'' OR CORP_Name2 LIKE N''%吴中%'' OR CORP_Precinct LIKE N''%吴中%'' OR CORP_RegOrgan LIKE N''%吴中%'')
'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_平江]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP_平江]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND (CORP_Addr LIKE N''%平江%'' OR CORP_Name2 LIKE N''%平江%'' OR CORP_Precinct LIKE N''%平江%'' OR CORP_RegOrgan LIKE N''%平江%'')
'
GO

-- 删除视图： CORP_沧浪1，CORP_相城1，CORP_新区1，CORP_园区1，CORP_金阊1，CORP_吴中1，CORP_平江1
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_沧浪1]'))
DROP VIEW [dbo].[CORP_沧浪1]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_相城1]'))
DROP VIEW [dbo].[CORP_相城1]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_新区1]'))
DROP VIEW [dbo].[CORP_新区1]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_园区1]'))
DROP VIEW [dbo].[CORP_园区1]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_金阊1]'))
DROP VIEW [dbo].[CORP_金阊1]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_吴中1]'))
DROP VIEW [dbo].[CORP_吴中1]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_平江1]'))
DROP VIEW [dbo].[CORP_平江1]
GO

-- 重建视图： CORP_沧浪1，CORP_相城1，CORP_新区1，CORP_园区1，CORP_金阊1，CORP_吴中1，CORP_平江1
-- 苏州7个区并且电话号码LIKE '13%'
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_沧浪1]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP_沧浪1]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND CORP_Tel LIKE ''13%'' AND (CORP_Addr LIKE N''%沧浪%'' OR CORP_Name2 LIKE N''%沧浪%'' OR CORP_Precinct LIKE N''%沧浪%'' OR CORP_RegOrgan LIKE N''%沧浪%'')
'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_相城1]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP_相城1]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND CORP_Tel LIKE ''13%'' AND (CORP_Addr LIKE N''%相城%'' OR CORP_Name2 LIKE N''%相城%'' OR CORP_Precinct LIKE N''%相城%'' OR CORP_RegOrgan LIKE N''%相城%'')'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_新区1]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP_新区1]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND CORP_Tel LIKE ''13%'' AND (CORP_Addr LIKE N''%新区%'' OR CORP_Name2 LIKE N''%新区%'' OR CORP_Precinct LIKE N''%新区%'' OR CORP_RegOrgan LIKE N''%新区%'')
'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_园区1]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP_园区1]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND CORP_Tel LIKE ''13%'' AND (CORP_Addr LIKE N''%园区%'' OR CORP_Name2 LIKE N''%园区%'' OR CORP_Precinct LIKE N''%园区%'' OR CORP_RegOrgan LIKE N''%园区%'')

'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_金阊1]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP_金阊1]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND CORP_Tel LIKE ''13%'' AND (CORP_Addr LIKE N''%金阊%'' OR CORP_Name2 LIKE N''%金阊%'' OR CORP_Precinct LIKE N''%金阊%'' OR CORP_RegOrgan LIKE N''%金阊%'')
'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_吴中1]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP_吴中1]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND CORP_Tel LIKE ''13%'' AND (CORP_Addr LIKE N''%吴中%'' OR CORP_Name2 LIKE N''%吴中%'' OR CORP_Precinct LIKE N''%吴中%'' OR CORP_RegOrgan LIKE N''%吴中%'')

'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP_平江1]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP_平江1]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND CORP_Tel LIKE ''13%'' AND (CORP_Addr LIKE N''%平江%'' OR CORP_Name2 LIKE N''%平江%'' OR CORP_Precinct LIKE N''%平江%'' OR CORP_RegOrgan LIKE N''%平江%'')
'
GO

-- 删除视图： CORP$吴江，CORP$常熟，CORP$张家港，CORP$昆山，CORP$太仓，CORP$吴县
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP$吴江]'))
DROP VIEW [dbo].[CORP$吴江]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP$常熟]'))
DROP VIEW [dbo].[CORP$常熟]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP$张家港]'))
DROP VIEW [dbo].[CORP$张家港]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP$昆山]'))
DROP VIEW [dbo].[CORP$昆山]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP$太仓]'))
DROP VIEW [dbo].[CORP$太仓]
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP$吴县]'))
DROP VIEW [dbo].[CORP$吴县]
GO

-- 重建视图： CORP$吴江，CORP$常熟，CORP$张家港，CORP$昆山，CORP$太仓，CORP$吴县
-- 苏州6县市
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP$吴江]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP$吴江]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND (CORP_Addr LIKE N''%吴江%'' OR CORP_Name2 LIKE N''%吴江%'' OR CORP_Precinct LIKE N''%吴江%'' OR CORP_RegOrgan LIKE N''%吴江%'')
'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP$常熟]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP$常熟]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND (CORP_Addr LIKE N''%常熟%'' OR CORP_Name2 LIKE N''%常熟%'' OR CORP_Precinct LIKE N''%常熟%'' OR CORP_RegOrgan LIKE N''%常熟%'')
'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP$张家港]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP$张家港]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND (CORP_Addr LIKE N''%张家港%'' OR CORP_Name2 LIKE N''%张家港%'' OR CORP_Precinct LIKE N''%张家港%'' OR CORP_RegOrgan LIKE N''%张家港%'')
'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP$昆山]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP$昆山]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND (CORP_Addr LIKE N''%昆山%'' OR CORP_Name2 LIKE N''%昆山%'' OR CORP_Precinct LIKE N''%昆山%'' OR CORP_RegOrgan LIKE N''%昆山%'')
'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP$太仓]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP$太仓]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND (CORP_Addr LIKE N''%太仓%'' OR CORP_Name2 LIKE N''%太仓%'' OR CORP_Precinct LIKE N''%太仓%'' OR CORP_RegOrgan LIKE N''%太仓%'')
'
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CORP$吴县]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[CORP$吴县]
AS
SELECT     CORP_ID, CORP_RegNo, CORP_Name1, CORP_Name2, CORP_Owner, CORP_Addr, CORP_Cap, CORP_Scope, CORP_Tel, CORP_PostCode, 
                      CORP_Industry, CORP_Precinct, CORP_CreateDate1, CORP_CreateDate2, CORP_RegOrgan, CORP_Category, CORP_Page, CORP_PageIdx, 
                      CORP_Status, CORP_Idx, CORP_Type
FROM         dbo.CORP
WHERE     CORP_Name1 NOT LIKE N''%***%'' AND CORP_Name1 <> '''' AND CORP_Name1 <> N''未填写'' 
	AND CORP_Name2 NOT LIKE N''%***%'' AND CORP_Name2 <> '''' AND CORP_Name2 <> N''未填写'' 
	AND CORP_Owner NOT LIKE N''%***%'' AND CORP_Owner <> '''' AND CORP_Owner <> N''未填写'' 
	AND (CORP_Addr LIKE N''%吴县%'' OR CORP_Name2 LIKE N''%吴县%'' OR CORP_Precinct LIKE N''%吴县%'' OR CORP_RegOrgan LIKE N''%吴县%'')
'
GO
