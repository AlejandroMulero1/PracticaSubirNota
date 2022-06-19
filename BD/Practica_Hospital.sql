CREATE DATABASE PracticaHospital
DROP DATABASE PracticaHospital

USE PracticaHospital

CREATE TABLE Empresa(
	CIF char(9) CONSTRAINT PK_Empresa PRIMARY KEY (CIF),
	NombreEmp varchar(50)
);

CREATE TABLE Envios(
	CIF char(9),
	CodEnvios char(8) CONSTRAINT PK_Envios PRIMARY KEY(CodEnvios),
	fechaEnvio date,
	CONSTRAINT FK_Envios_Empresa
	FOREIGN KEY (CIF) REFERENCES Empresa(CIF)
);

CREATE TABLE Facturas(
	CodEnvios char(8),
	CodFactura char(11) CONSTRAINT PK_Facturas PRIMARY KEY(CodFactura),
	importeSinIva money,
	importeConIva money,
	CONSTRAINT FK_Facturas_Envios
	FOREIGN KEY (CodEnvios) REFERENCES Envios(CodEnvios)
);
GO


CREATE OR ALTER FUNCTION selectCodFactura (@pos int, @codEnvio char(11))
RETURNS char(11)
AS BEGIN
	RETURN (SELECT codFactura FROM (SELECT codFactura, ROW_NUMBER() OVER (ORDER BY codFactura) AS pos, CodEnvios FROM Facturas WHERE CodEnvios=@codEnvio) a WHERE pos=@pos AND CodEnvios=@codEnvio)
	END
GO


GO
CREATE OR ALTER FUNCTION DevolverTodo ()
RETURNS @tabla TABLE(
		CIF char(9),
		NombreEmp varchar(50),
		CodEnvios char(8),
		fechaEnvio date,
		CodsFacturas varchar(55),
		importeSinIva money,
		importeConIva money
		)
AS
BEGIN
		INSERT INTO @tabla
		SELECT DISTINCT
			E.CIF,
			E.NombreEmp,
			En.CodEnvios,
			En.fechaEnvio,
			CONCAT(dbo.selectCodFactura(1, En.codEnvios), ',  ', dbo.selectCodFactura(2, En.codEnvios), ',  ', dbo.selectCodFactura(3, En.codEnvios), ',  ', dbo.selectCodFactura(4, En.codEnvios)) AS CodsFacturas,
			(SELECT SUM(importeSinIva) FROM Facturas AS T GROUP BY T.CodEnvios HAVING EN.CodEnvios=T.CodEnvios)AS ImpTotalSinIva,
			(SELECT SUM(importeSinIva) FROM Facturas AS T GROUP BY T.CodEnvios HAVING EN.CodEnvios=T.CodEnvios)AS ImpTotalConIva 

		FROM Empresa AS E INNER JOIN
			 Envios AS En ON E.CIF=En.CIF INNER JOIN
			 Facturas AS F ON En.CodEnvios = F.CodEnvios

	RETURN
END
GO


INSERT INTO Empresa
VALUES
('Q55667788', 'Sanofi'),
('B12345678', 'Novartis')


INSERT INTO Envios
VALUES
('Q55667788', '20220503','05-05-2005'),
('B12345678', '20220501', '06-06-2006')


INSERT INTO Facturas
VALUES
('20220503', 'FC1SANO0503',	13652.35,	5495.20),
('20220503', 'FC1SANO0504',	54632.00,	7485.70),
('20220503', 'FC1SANO0505',	72038.00,	4256.80),
('20220503', 'FC1SANO0506',	85937.00,	7777.45),
('20220501', 'FC1NOVA0507',	4110.75,	7568.55),
('20220501', 'FC1NOVA0508',	7492.70,	1345.55),
('20220501', 'FC1NOVA0509',	9435.95,	6578.55),
('20220501', 'FC1NOVA0510',	4284.41,	8555.98)



	CREATE TABLE FacturaFinal(
	CIF char(9),
		NombreEmp varchar(50),
		CodEnvios char(8),
		fechaEnvio date,
		CodsFacturas varchar(55),
		importeSinIva money,
		importeConIva money,
		bonificacion money
	);
