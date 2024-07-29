USE TyperF1;
GO

--max points non-Sprint without joker
SELECT p.Name, p.Surname, gp.Name AS GrandPrixName, s.Year, SUM(pt.Number) AS PointsSum
FROM Participant p
INNER JOIN Points pt ON p.Id = pt.ParticipantId
INNER JOIN Session ses ON pt.SessionId = ses.Id
INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id
INNER JOIN Season s ON gp.SeasonId = s.Id
LEFT JOIN Joker j ON p.Id = j.ParticipantId AND j.GrandPrixId = gp.Id
WHERE ses.Name != 'Sprint'
AND gp.Id NOT IN (
    SELECT DISTINCT gp2.Id
    FROM GrandPrix gp2
    INNER JOIN Session ses2 ON gp2.Id = ses2.GrandPrixId
    INNER JOIN Joker j2 ON gp2.Id = j2.GrandPrixId
    WHERE ses2.Name = 'Sprint'
)
GROUP BY p.Name, p.Surname, gp.Name, s.Year
HAVING SUM(pt.Number) = (
    SELECT MAX(PointsSum) 
    FROM (
        SELECT gp3.Id, p3.Id AS ParticipantId, SUM(pt3.Number) AS PointsSum 
        FROM Participant p3
        INNER JOIN Points pt3 ON p3.Id = pt3.ParticipantId
        INNER JOIN Session ses3 ON pt3.SessionId = ses3.Id
        INNER JOIN GrandPrix gp3 ON ses3.GrandPrixId = gp3.Id
        INNER JOIN Season s3 ON gp3.SeasonId = s3.Id
        LEFT JOIN Joker j3 ON p3.Id = j3.ParticipantId AND j3.GrandPrixId = gp3.Id
        WHERE ses3.Name != 'Sprint'
        AND gp3.Id NOT IN (
            SELECT DISTINCT gp4.Id
            FROM GrandPrix gp4
            INNER JOIN Session ses4 ON gp4.Id = ses4.GrandPrixId
            INNER JOIN Joker j4 ON gp4.Id = j4.GrandPrixId
            WHERE ses4.Name = 'Sprint'
        )
        GROUP BY gp3.Id, p3.Id
    ) AS SubQuery
);


--min points non-Sprint without joker
SELECT p.Name, p.Surname, gp.Name AS GrandPrixName, s.Year, SUM(pt.Number) AS PointsSum
FROM Participant p
INNER JOIN Points pt ON p.Id = pt.ParticipantId
INNER JOIN Session ses ON pt.SessionId = ses.Id
INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id
INNER JOIN Season s ON gp.SeasonId = s.Id
LEFT JOIN Joker j ON p.Id = j.ParticipantId AND j.GrandPrixId = gp.Id
WHERE ses.Name != 'Sprint'
AND gp.Id NOT IN (
    SELECT DISTINCT gp2.Id
    FROM GrandPrix gp2
    INNER JOIN Session ses2 ON gp2.Id = ses2.GrandPrixId
    INNER JOIN Joker j2 ON gp2.Id = j2.GrandPrixId
    WHERE ses2.Name = 'Sprint'
)
GROUP BY p.Name, p.Surname, gp.Name, s.Year
HAVING SUM(pt.Number) = (
    SELECT MIN(PointsSum) 
    FROM (
        SELECT gp3.Id, p3.Id AS ParticipantId, SUM(pt3.Number) AS PointsSum 
        FROM Participant p3
        INNER JOIN Points pt3 ON p3.Id = pt3.ParticipantId
        INNER JOIN Session ses3 ON pt3.SessionId = ses3.Id
        INNER JOIN GrandPrix gp3 ON ses3.GrandPrixId = gp3.Id
        INNER JOIN Season s3 ON gp3.SeasonId = s3.Id
        LEFT JOIN Joker j3 ON p3.Id = j3.ParticipantId AND j3.GrandPrixId = gp3.Id
        WHERE ses3.Name != 'Sprint'
        AND gp3.Id NOT IN (
            SELECT DISTINCT gp4.Id
            FROM GrandPrix gp4
            INNER JOIN Session ses4 ON gp4.Id = ses4.GrandPrixId
            INNER JOIN Joker j4 ON gp4.Id = j4.GrandPrixId
            WHERE ses4.Name = 'Sprint'
        )
        GROUP BY gp3.Id, p3.Id
    ) AS SubQuery
);