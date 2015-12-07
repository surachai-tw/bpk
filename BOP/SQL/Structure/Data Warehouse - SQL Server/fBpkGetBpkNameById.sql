SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[fBpkGetBpkNameById]
(
	@V_bpk_id smallint
)
RETURNS nvarchar(255)
AS
BEGIN		
	IF @V_bpk_id = 1 
		BEGIN
			RETURN 'BPK 1' 
		END
	ELSE IF @V_bpk_id = 3 
		BEGIN 
			RETURN 'BPK 3' 
		END
	ELSE IF @V_bpk_id = 8 
		BEGIN 
			RETURN 'BPK 8' 
		END
	ELSE IF @V_bpk_id = 9
		BEGIN 
			RETURN 'BPK 9' 
		END 

	RETURN ' ' 
END

