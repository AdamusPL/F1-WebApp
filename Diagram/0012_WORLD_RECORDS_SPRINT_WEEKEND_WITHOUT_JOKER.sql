USE TyperF1;
GO

--max points Sprint weekend without joker
SELECT Participant.Name, Participant.Surname, GrandPrix.Name, Season.Year, SUM(Points.Number) AS PointsSum FROM Participant
INNER JOIN Points ON Participant.Id = Points.ParticipantId
INNER JOIN Session ON Points.SessionId = Session.Id
INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE GrandPrix.Id != (
	SELECT GrandPrix.Id FROM Joker j
	INNER JOIN GrandPrix ON GrandPrix.Id = j.GrandPrixId
)
AND Session.Name = 'Sprint'
GROUP BY GrandPrix.Name, Participant.Name, Participant.Surname, Season.Year
HAVING SUM(Points.Number) = (
	SELECT MAX(PointsSum) FROM (
	SELECT GrandPrix.Id, Participant.Id AS ParticipantId, SUM(Points.Number) AS PointsSum FROM Participant
    INNER JOIN Points ON Participant.Id = Points.ParticipantId
	INNER JOIN Session ON Points.SessionId = Session.Id
	INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
	INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
	WHERE GrandPrix.Id != (
	SELECT GrandPrix.Id FROM Joker j
	INNER JOIN GrandPrix ON GrandPrix.Id = j.GrandPrixId
	)
	AND Session.Name = 'Sprint'
    GROUP BY GrandPrix.Id, Participant.Id
    ) AS SubQuery
);

--min points Sprint weekend without joker
SELECT Participant.Name, Participant.Surname, GrandPrix.Name, Season.Year, SUM(Points.Number) AS PointsSum FROM Participant
INNER JOIN Points ON Participant.Id = Points.ParticipantId
INNER JOIN Session ON Points.SessionId = Session.Id
INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
WHERE GrandPrix.Id != (
	SELECT GrandPrix.Id FROM Joker j
	INNER JOIN GrandPrix ON GrandPrix.Id = j.GrandPrixId
)
AND Session.Name = 'Sprint'
GROUP BY GrandPrix.Name, Participant.Name, Participant.Surname, Season.Year
HAVING SUM(Points.Number) = (
	SELECT MIN(PointsSum) FROM (
	SELECT GrandPrix.Id, Participant.Id AS ParticipantId, SUM(Points.Number) AS PointsSum FROM Participant
    INNER JOIN Points ON Participant.Id = Points.ParticipantId
	INNER JOIN Session ON Points.SessionId = Session.Id
	INNER JOIN GrandPrix ON Session.GrandPrixId = GrandPrix.Id
	INNER JOIN Season ON GrandPrix.SeasonId = Season.Id
	WHERE GrandPrix.Id != (
	SELECT GrandPrix.Id FROM Joker j
	INNER JOIN GrandPrix ON GrandPrix.Id = j.GrandPrixId
	)
	AND Session.Name = 'Sprint'
    GROUP BY GrandPrix.Id, Participant.Id
    ) AS SubQuery
);
