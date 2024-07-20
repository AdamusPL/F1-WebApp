USE TyperF1;
GO

SELECT * FROM Points;

SELECT Participant.Name, Participant.Surname, GrandPrix.Name, Season.Year, Points.Number FROM Participant
INNER JOIN Points ON Participant.Id = Points.ParticipantId
INNER JOIN Session ON Points.SessionId = Session.Id
INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE Points.Number = (SELECT MAX(Points.Number) FROM Points
INNER JOIN Session ON Points.SessionId = Session.Id
WHERE Session.Name = 'Race'
);

SELECT Participant.Name, Participant.Surname, GrandPrix.Name, Season.Year, Points.Number FROM Participant
INNER JOIN Points ON Participant.Id = Points.ParticipantId
INNER JOIN Session ON Points.SessionId = Session.Id
INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE Points.Number = (SELECT MIN(Points.Number) FROM Points
INNER JOIN Session ON Points.SessionId = Session.Id
WHERE Session.Name = 'Race'
);