USE TyperF1;
GO

SELECT * FROM Points;

--max points race no matter if joker was used
SELECT Participant.Name, Participant.Surname, GrandPrix.Name, Season.Year, Points.Number FROM Participant
INNER JOIN Points ON Participant.Id = Points.ParticipantId
INNER JOIN Session ON Points.SessionId = Session.Id
INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE Points.Number = (SELECT MAX(Points.Number) FROM Points
INNER JOIN Session ON Points.SessionId = Session.Id
WHERE Session.Name = 'Race'
);

--min points race no matter if joker was used
SELECT Participant.Name, Participant.Surname, GrandPrix.Name, Season.Year, Points.Number FROM Participant
INNER JOIN Points ON Participant.Id = Points.ParticipantId
INNER JOIN Session ON Points.SessionId = Session.Id
INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE Points.Number = (SELECT MIN(Points.Number) FROM Points
INNER JOIN Session ON Points.SessionId = Session.Id
WHERE Session.Name = 'Race'
);

--races with joker used
SELECT GrandPrix.Name, Participant.Name, Participant.Surname, COUNT(Joker.Id) FROM Participant
INNER JOIN Joker ON Participant.Id = Joker.ParticipantId
INNER JOIN GrandPrix ON GrandPrix.Id = Joker.GrandPrixId
GROUP BY GrandPrix.Name, Participant.Name, Participant.Surname;

--max points race with joker
SELECT p.Name, p.Surname, gp.Name AS GrandPrixName, s.Year, pt.Number AS Points
FROM Participant p
INNER JOIN Points pt ON p.Id = pt.ParticipantId
INNER JOIN Session ses ON pt.SessionId = ses.Id
INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id
INNER JOIN Season s ON gp.SeasonId = s.Id
INNER JOIN Joker j ON p.Id = j.ParticipantId
WHERE ses.Name = 'Race' 
AND pt.Number = (
    SELECT MAX(pt2.Number)
    FROM Points pt2
    INNER JOIN Session ses2 ON pt2.SessionId = ses2.Id
	INNER JOIN GrandPrix gp2 ON ses2.GrandPrixId = gp2.Id
    INNER JOIN Joker j2 ON gp2.Id = j2.GrandPrixId
    WHERE ses2.Name = 'Race'
);

--max points qualifying with joker
SELECT p.Name, p.Surname, gp.Name AS GrandPrixName, s.Year, pt.Number AS Points
FROM Participant p
INNER JOIN Points pt ON p.Id = pt.ParticipantId
INNER JOIN Session ses ON pt.SessionId = ses.Id
INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id
INNER JOIN Season s ON gp.SeasonId = s.Id
INNER JOIN Joker j ON p.Id = j.ParticipantId
WHERE ses.Name = 'Qualifying' 
AND pt.Number = (
    SELECT MAX(pt2.Number)
    FROM Points pt2
    INNER JOIN Session ses2 ON pt2.SessionId = ses2.Id
	INNER JOIN GrandPrix gp2 ON ses2.GrandPrixId = gp2.Id
    INNER JOIN Joker j2 ON gp2.Id = j2.GrandPrixId
    WHERE ses2.Name = 'Qualifying'
);

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

--max points qualifying without joker
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
