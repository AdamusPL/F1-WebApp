USE TyperF1;
GO

SELECT * FROM ParticipantLoginData;
SELECT * FROM Season;
SELECT * FROM GrandPrix;
SELECT * FROM Participant;
SELECT * FROM Email;
SELECT * FROM Predictions;
SELECT * FROM Session;
SELECT * FROM Track;
SELECT * FROM Points;
SELECT * FROM Joker;

INSERT INTO Joker VALUES(3, 15);
UPDATE Predictions SET PointsId=NULL WHERE Id=7 OR Id=8;
DELETE FROM Points WHERE Id=7 OR Id=8;