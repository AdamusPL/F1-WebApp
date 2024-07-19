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

SELECT GrandPrix.Name, Participant.Name, Participant.Surname, COUNT(Joker.Id) FROM Participant
INNER JOIN Joker ON Participant.Id = Joker.ParticipantId
INNER JOIN GrandPrix ON Joker.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE Season.Year=2024
GROUP BY GrandPrix.Name, Session.Name, Participant.Name, Participant.Surname
ORDER BY GrandPrix.Name DESC;

SELECT GrandPrix.Name, Participant.Name, Participant.Surname, SUM(Points.Number) FROM Participant
INNER JOIN Points ON Participant.Id = Points.ParticipantId
INNER JOIN Session ON Points.SessionId = Session.Id
INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE Season.Year=2024 AND GrandPrix.Name LIKE 'Bahrain'
GROUP BY GrandPrix.Name, Participant.Name, Participant.Surname
ORDER BY GrandPrix.Name, SUM(Points.Number) DESC;

SELECT GrandPrix.Name, Participant.Name, Participant.Surname, COUNT(Joker.Id) FROM Participant
INNER JOIN Joker ON Participant.Id = Joker.ParticipantId
INNER JOIN GrandPrix ON GrandPrix.Id = Joker.GrandPrixId
GROUP BY GrandPrix.Name, Participant.Name, Participant.Surname;

SELECT Season.Id, Participant.Name, Participant.Surname, SUM(Points.Number) FROM Participant
INNER JOIN Points ON Participant.Id = Points.ParticipantId
INNER JOIN Session ON Points.SessionId = Session.Id
INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE Season.Year=2024
GROUP BY Season.Id, Participant.Name, Participant.Surname
ORDER BY Season.Id, SUM(Points.Number) DESC;

SELECT Participant.Name, Participant.Surname, COUNT(Joker.Id) FROM Participant
INNER JOIN Joker ON Participant.Id = Joker.ParticipantId
INNER JOIN GrandPrix ON GrandPrix.Id = Joker.GrandPrixId
INNER JOIN Season ON Season.Id = GrandPrix.SeasonId
WHERE Season.Year = 2024
GROUP BY Participant.Name, Participant.Surname;

SELECT t1.Name, t1.Surname, PointsSum, JokersUsed FROM
(SELECT Season.Id, Participant.Name, Participant.Surname, SUM(Points.Number) AS 'PointsSum' FROM Participant
INNER JOIN Points ON Participant.Id = Points.ParticipantId
INNER JOIN Session ON Points.SessionId = Session.Id
INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE Season.Year=2024
GROUP BY Season.Id, Participant.Name, Participant.Surname) t1
INNER JOIN
(SELECT Participant.Name, Participant.Surname, COUNT(Joker.Id) AS 'JokersUsed' FROM Participant
INNER JOIN Joker ON Participant.Id = Joker.ParticipantId
INNER JOIN GrandPrix ON GrandPrix.Id = Joker.GrandPrixId
INNER JOIN Season ON Season.Id = GrandPrix.SeasonId
WHERE Season.Year = 2024
GROUP BY Participant.Name, Participant.Surname) t2
ON t1.Name = t2.Name;