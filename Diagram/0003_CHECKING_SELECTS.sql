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

SELECT * FROM Points WHERE Number=16;

INSERT INTO Joker VALUES(3, 15);
DELETE FROM Predictions WHERE Id=49;
DELETE FROM Points WHERE Id=46;