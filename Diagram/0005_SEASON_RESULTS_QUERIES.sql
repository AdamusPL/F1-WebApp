SELECT Season.Year, GrandPrix.Name, Session.Name, Participant.Name, Participant.Surname, Points.Number FROM Participant
INNER JOIN Points ON Participant.Id = Points.ParticipantId
INNER JOIN Session ON Points.SessionId = Session.Id
INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE Season.Year=2024
ORDER BY Session.Id, Points.Number DESC;

SELECT GrandPrix.Name, Participant.Name, Participant.Surname FROM Participant
INNER JOIN Joker ON Participant.Id = Joker.ParticipantId
INNER JOIN GrandPrix ON Joker.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE Season.Year=2024
GROUP BY GrandPrix.Name, Participant.Name, Participant.Surname;

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