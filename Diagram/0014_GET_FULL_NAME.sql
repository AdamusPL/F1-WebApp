USE TyperF1;
GO

SELECT * FROM Participant
INNER JOIN ParticipantLoginData ON Participant.UserId = ParticipantLoginData.Id
WHERE Participant.Name = 'Andrzej' AND Participant.Surname = 'Borowczyk';