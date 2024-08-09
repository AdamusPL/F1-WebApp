USE TyperF1;
GO

--races with joker used
SELECT GrandPrix.Name, Participant.Name, Participant.Surname, COUNT(Joker.Id) FROM Participant
INNER JOIN Joker ON Participant.Id = Joker.ParticipantId
INNER JOIN GrandPrix ON GrandPrix.Id = Joker.GrandPrixId
GROUP BY GrandPrix.Name, Participant.Name, Participant.Surname;

--max points race with joker
SELECT p.Name, p.Surname, gp.Name AS GrandPrixName, s.Year, MAX(pt.Number) AS Points
FROM Participant p
INNER JOIN Points pt ON p.Id = pt.ParticipantId
INNER JOIN Session ses ON pt.SessionId = ses.Id
INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id
INNER JOIN Season s ON gp.SeasonId = s.Id
INNER JOIN Joker j ON j.ParticipantId = p.Id
WHERE ses.Name = 'Race'
AND gp.Id = (
	SELECT GrandPrix.Id FROM Joker j
	INNER JOIN GrandPrix ON GrandPrix.Id = j.GrandPrixId
)
GROUP BY p.Name, p.Surname, gp.Name, s.Year;

--max points qualifying with joker
SELECT p.Name, p.Surname, gp.Name AS GrandPrixName, s.Year, pt.Number AS Points
FROM Participant p
INNER JOIN Points pt ON p.Id = pt.ParticipantId
INNER JOIN Session ses ON pt.SessionId = ses.Id
INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id
INNER JOIN Season s ON gp.SeasonId = s.Id
INNER JOIN Joker j ON j.ParticipantId = p.Id
WHERE ses.Name = 'Qualifying'
AND gp.Id = (
	SELECT GrandPrix.Id FROM Joker j
	INNER JOIN GrandPrix ON GrandPrix.Id = j.GrandPrixId
);