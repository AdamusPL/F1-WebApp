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
SELECT * FROM Session;
SELECT * FROM Joker;

DELETE FROM Predictions WHERE Id=32;
DELETE FROM Points WHERE Id=28;