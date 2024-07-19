SELECT Participant.Name, Participant.Surname, MAX(Points.Number) FROM Participant
INNER JOIN Points ON Participant.Id = Points.ParticipantId
GROUP BY Participant.Name, Participant.Surname;