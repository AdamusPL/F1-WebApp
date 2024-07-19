USE TyperF1;
GO

SELECT * FROM ParticipantLoginData;
SELECT * FROM Season;
SELECT * FROM GrandPrix;
SELECT * FROM Participant;
SELECT * FROM Session;
SELECT * FROM Track;
SELECT * FROM Points;
SELECT * FROM Session;

SELECT Season.Year, GrandPrix.Name, Session.Name, Participant.Name, Participant.Surname, Points.Number FROM Participant
INNER JOIN Points ON Participant.Id = Points.ParticipantId
INNER JOIN Session ON Points.SessionId = Session.Id
INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE Season.Year=2024
ORDER BY Session.Id, Points.Number DESC;

SELECT GrandPrix.Id, Participant.Name, Participant.Surname, SUM(Points.Number) FROM Participant
INNER JOIN Points ON Participant.Id = Points.ParticipantId
INNER JOIN Session ON Points.SessionId = Session.Id
INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE Season.Year=2024
GROUP BY GrandPrix.Id, Participant.Name, Participant.Surname
ORDER BY GrandPrix.Id, SUM(Points.Number) DESC;

SELECT Season.Id, Participant.Name, Participant.Surname, SUM(Points.Number) FROM Participant
INNER JOIN Points ON Participant.Id = Points.ParticipantId
INNER JOIN Session ON Points.SessionId = Session.Id
INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE Season.Year=2024
GROUP BY Season.Id, Participant.Name, Participant.Surname
ORDER BY Season.Id, SUM(Points.Number) DESC;