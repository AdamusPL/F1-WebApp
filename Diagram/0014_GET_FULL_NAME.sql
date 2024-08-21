USE TyperF1;
GO

SELECT * FROM Participant
INNER JOIN ParticipantLoginData ON Participant.UserId = ParticipantLoginData.Id
WHERE Participant.Name = 'Andrzej' AND Participant.Surname = 'Borowczyk';

SELECT Name, Surname, Username, Email FROM Participant
INNER JOIN ParticipantLoginData ON Participant.UserId = ParticipantLoginData.Id
INNER JOIN Email ON Participant.EmailId = Email.Id;

SELECT * FROM Participant;
SELECT * FROM ParticipantLoginData;
SELECT * FROM Email;