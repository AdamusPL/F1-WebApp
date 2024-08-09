USE TyperF1;
GO

--max points race without joker
SELECT p.Name, p.Surname, gp.Name AS GrandPrixName, s.Year, pt.Number AS Points
FROM Participant p
INNER JOIN Points pt ON p.Id = pt.ParticipantId
INNER JOIN Session ses ON pt.SessionId = ses.Id
INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id
INNER JOIN Season s ON gp.SeasonId = s.Id
INNER JOIN Joker j ON p.Id = j.ParticipantId
WHERE pt.Number = (SELECT MAX(pt.Number) AS Points
FROM Participant p
INNER JOIN Points pt ON p.Id = pt.ParticipantId
INNER JOIN Session ses ON pt.SessionId = ses.Id
INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id
INNER JOIN Season s ON gp.SeasonId = s.Id
WHERE ses.Name = 'Race'
AND p.Id NOT IN (
    SELECT j.ParticipantId
    FROM Joker j
    INNER JOIN GrandPrix gp2 ON j.GrandPrixId = gp2.Id
    WHERE gp2.Id = gp.Id
));

--max points qualifying without joker (doesn't work?)
SELECT p.Name, p.Surname, gp.Name AS GrandPrixName, s.Year, pt.Number AS Points
FROM Participant p
INNER JOIN Points pt ON p.Id = pt.ParticipantId
INNER JOIN Session ses ON pt.SessionId = ses.Id
INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id
INNER JOIN Season s ON gp.SeasonId = s.Id
INNER JOIN Joker j ON p.Id = j.ParticipantId
WHERE pt.Number = (SELECT MAX(pt.Number) AS Points
FROM Participant p
INNER JOIN Points pt ON p.Id = pt.ParticipantId
INNER JOIN Session ses ON pt.SessionId = ses.Id
INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id
INNER JOIN Season s ON gp.SeasonId = s.Id
WHERE ses.Name = 'Qualifying'
AND p.Id NOT IN (
    SELECT j.ParticipantId
    FROM Joker j
    INNER JOIN GrandPrix gp2 ON j.GrandPrixId = gp2.Id
    WHERE gp2.Id = gp.Id
));


--min points race without joker
SELECT p.Name, p.Surname, gp.Name AS GrandPrixName, s.Year, pt.Number AS Points
FROM Participant p
INNER JOIN Points pt ON p.Id = pt.ParticipantId
INNER JOIN Session ses ON pt.SessionId = ses.Id
INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id
INNER JOIN Season s ON gp.SeasonId = s.Id
INNER JOIN Joker j ON p.Id = j.ParticipantId
WHERE pt.Number = (SELECT MIN(pt.Number) AS Points
FROM Participant p
INNER JOIN Points pt ON p.Id = pt.ParticipantId
INNER JOIN Session ses ON pt.SessionId = ses.Id
INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id
INNER JOIN Season s ON gp.SeasonId = s.Id
WHERE ses.Name = 'Race'
AND p.Id NOT IN (
    SELECT j.ParticipantId
    FROM Joker j
    INNER JOIN GrandPrix gp2 ON j.GrandPrixId = gp2.Id
    WHERE gp2.Id = gp.Id
));


--min points qualifying without joker
SELECT p.Name, p.Surname, gp.Name AS GrandPrixName, s.Year, pt.Number AS Points
FROM Participant p
INNER JOIN Points pt ON p.Id = pt.ParticipantId
INNER JOIN Session ses ON pt.SessionId = ses.Id
INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id
INNER JOIN Season s ON gp.SeasonId = s.Id
INNER JOIN Joker j ON p.Id = j.ParticipantId
WHERE pt.Number = (SELECT MIN(pt.Number) AS Points
FROM Participant p
INNER JOIN Points pt ON p.Id = pt.ParticipantId
INNER JOIN Session ses ON pt.SessionId = ses.Id
INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id
INNER JOIN Season s ON gp.SeasonId = s.Id
WHERE ses.Name = 'Qualifying'
AND p.Id NOT IN (
    SELECT j.ParticipantId
    FROM Joker j
    INNER JOIN GrandPrix gp2 ON j.GrandPrixId = gp2.Id
    WHERE gp2.Id = gp.Id
));