CREATE USER trms_user
IDENTIFIED BY trms_password
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;

GRANT connect to trms_user;
GRANT resource to trms_user;
GRANT create session TO trms_user;
GRANT create table TO trms_user;
GRANT create view TO trms_user;

GRANT dba TO trms_user;

conn trms_user/trms_password



create table AdminInfo (
    AdminInfoID NUMBER NOT NULL,
    AdminUsername VARCHAR2(16),
    AdminPassword VARCHAR2(128),
    CONSTRAINT PK_AdminInfo PRIMARY KEY (AdminInfoID)
);

create sequence SQ_ADMININFO_PK start with 1 increment by 1;

create or replace trigger TR_Insert_AdminInfo before insert on AdminInfo
for each row begin
select SQ_ADMININFO_PK.nextval into :new.AdminInfoID from dual;
end;
/

create table Department (
    DepartmentID NUMBER NOT NULL,
    DepartmentName VARCHAR2(64),
    CONSTRAINT PK_Department PRIMARY KEY (DepartmentID)
);

create sequence SQ_DEPARTMENT_PK start with 1 increment by 1;

create or replace trigger TR_Insert_Department before insert on Department
for each row begin
select SQ_DEPARTMENT_PK.nextval into :new.DepartmentID from dual;
end;
/

create table Employee (
    EmployeeID NUMBER NOT NULL,
    FirstName VARCHAR2(32) NOT NULL,
    LastName VARCHAR2(32) NOT NULL,
    Email VARCHAR2(64) NOT NULL,
    EmployeePassword VARCHAR2(128) NOT NULL,
    Department NUMBER NOT NULL,
    DirectSupervisor NUMBER NOT NULL,
    CONSTRAINT PK_Employee PRIMARY KEY (EmployeeID),
    CONSTRAINT FK_Department FOREIGN KEY (Department) REFERENCES Department(DepartmentID),
    CONSTRAINT FK_DirectSupervisor FOREIGN KEY (DirectSupervisor) REFERENCES Employee(EmployeeID)
);

create sequence SQ_EMPLOYEE_PK start with 1 increment by 1;

create or replace trigger TR_Insert_Employee before insert on Employee
for each row begin
select SQ_EMPLOYEE_PK.nextval into :new.EmployeeID from dual;
end;
/

create table BenCo (
    BenCoID NUMBER NOT NULL,
    BenCoEmployee NUMBER NOT NULL,
    CONSTRAINT PK_BenCo PRIMARY KEY (BencoID),
    CONSTRAINT FK_BenCoEmployee FOREIGN KEY (BenCoEmployee) REFERENCES Employee(EmployeeID)
);

create sequence SQ_BENCO_PK start with 1 increment by 1;

create or replace trigger TR_Insert_BenCo before insert on BenCo
for each row begin
select SQ_BENCO_PK.nextval into :new.BenCoID from dual;
end;
/

create table DepartmentHead (
    DepartmentHeadID NUMBER NOT NULL,
    DHDepartment NUMBER NOT NULL,
    DHEmployee NUMBER NOT NULL,
    CONSTRAINT PK_DepartmentHead PRIMARY KEY (DepartmentHeadID),
    CONSTRAINT FK_DHDepartment FOREIGN KEY (DHDepartment) REFERENCES Department(DepartmentID),
    CONSTRAINT FK_DHEmployee FOREIGN KEY (DHEmployee) REFERENCES Employee(EmployeeID)
);

create sequence SQ_DEPARTMENTHEAD_PK start with 1 increment by 1;

create or replace trigger TR_Insert_DepartmentHead before insert on DepartmentHead
for each row begin
select SQ_DEPARTMENTHEAD_PK.nextval into :new.DepartmentHeadID from dual;
end;
/

create table GradingFormat (
    GradingFormatID NUMBER NOT NULL,
    GradingFormatName VARCHAR2(32) NOT NULL,
    DefaultPassingGrade VARCHAR2(8) DEFAULT '70',
    RequiresPresentation INTEGER DEFAULT 0,
    CONSTRAINT PK_GradingFormat PRIMARY KEY (GradingFormatID),
    CONSTRAINT CHK_RequiresPresentation CHECK (RequiresPresentation>=0 AND RequiresPresentation<=1)
);

create sequence SQ_GRADINGFORMAT_PK start with 1 increment by 1;

create or replace trigger TR_Insert_GradingFormat before insert on GradingFormat
for each row begin
select SQ_GRADINGFORMAT_PK.nextval into :new.GradingFormatID from dual;
end;
/

create table EventType (
    EventTypeID NUMBER NOT NULL,
    EventTypeName VARCHAR2(32) NOT NULL,
    Coverage NUMBER DEFAULT 30,
    CONSTRAINT PK_EventType PRIMARY KEY (EventTypeID),
    CONSTRAINT CHK_Coverage CHECK (Coverage>=0 AND Coverage <=100)
);

create sequence SQ_EVENTTYPE_PK start with 1 increment by 1;

create or replace trigger TR_Insert_EventType before insert on EventType
for each row begin
select SQ_EVENTTYPE_PK.nextval into :new.EventTypeID from dual;
end;
/

create table Request (
    RequestID NUMBER NOT NULL,
    
    EventLocation VARCHAR2(256) NOT NULL,
    TrainingTimeStart DATE NOT NULL,
    TrainingTimeEnd DATE,
    EstimatedWorkHoursMissed NUMBER,
    EventDescription VARCHAR2(512) NOT NULL,
    EventType NUMBER NOT NULL,
    GradingFormat NUMBER NOT NULL,
    Grade VARCHAR(8),
    PassingGrade VARCHAR(8),
    
    Employee NUMBER NOT NULL,
    TimeRequestSent DATE NOT NULL,
    RequestState VARCHAR2(10) DEFAULT 'Submitted',
    AmountRequested NUMBER NOT NULL,
    Justification VARCHAR2(512),
    
    ReasonDenied VARCHAR2(1024),
    AmountAwarded NUMBER,
    ReasonExceedsFunds VARCHAR2(1024),
    Cancelled INTEGER,
    
    CONSTRAINT PK_Request PRIMARY KEY (RequestID),
    CONSTRAINT FK_RequestEventType FOREIGN KEY (EventType) REFERENCES EventType(EventTypeID),
    CONSTRAINT FK_RequestGradingFormat FOREIGN KEY (GradingFormat) REFERENCES GradingFormat(GradingFormatID),
    CONSTRAINT FK_RequestEmployee FOREIGN KEY (Employee) REFERENCES Employee(EmployeeID),
    CONSTRAINT CHK_RequestCancelled CHECK (Cancelled >= 0 AND Cancelled <= 1)
);

create sequence SQ_Request_PK start with 1 increment by 1;

create or replace trigger TR_Insert_Request before insert on Request
for each row begin
select SQ_Request_PK.nextval into :new.RequestID from dual;
end;
/

INSERT INTO AdminInfo (AdminUsername, AdminPassword) VALUES ('Disney', 'money');

INSERT INTO EventType (EventTypeName, Coverage) VALUES ('University Course', 80);
INSERT INTO EventType (EventTypeName, Coverage) VALUES ('Seminar', 60);
INSERT INTO EventType (EventTypeName, Coverage) VALUES ('Certification Preparation Class', 75);
INSERT INTO EventType (EventTypeName, Coverage) VALUES ('Certification', 100);
INSERT INTO EventType (EventTypeName, Coverage) VALUES ('Technical Training', 90);
INSERT INTO EventType (EventTypeName) VALUES ('Other');

INSERT INTO GradingFormat (GradingFormatName) VALUES ('Standard Test Format');
INSERT INTO GradingFormat (GradingFormatName, RequiresPresentation) VALUES ('Standard Presentation Format', 1);

INSERT INTO Department (DepartmentName) VALUES ('Aerospace');
INSERT INTO Department (DepartmentName) VALUES ('Public Relations');
INSERT INTO Department (DepartmentName) VALUES ('Intellectual Property');

INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Stan', 'Lee', 'stanlee@shield.gov', 'stanleepassword', 3, 1);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Carol', 'Danvers', 'caroldanvers@shield.gov', 'caroldanverspassword', 1, 1);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Nicholas', 'Fury', 'nicholasfury@shield.gov', 'nicholasfurypassword', 2, 1);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Maria', 'Hill', 'mariahill@shield.gov', 'mariahillpassword', 2, 3);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Thor', 'Odinson', 'thorodinson@shield.gov', 'thorodinsonpassword', 1, 2);

INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Peter', 'Quill', 'peterquill@shield.gov', 'peterquillpassword', 1, 2);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Steve', 'Rogers', 'steverogers@shield.gov', 'steverogerspassword', 2, 3);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Phil', 'Coulson', 'philcoulson@shield.gov', 'philcoulsonpassword', 2, 3);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Fandral', 'Asgard', 'fandralasgard@shield.gov', 'fandralasgardpassword', 1, 5);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Hogun', 'Asgard', 'hogunasgard@shield.gov', 'hogunasgardpassword', 1, 5);

INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Volstagg', 'Asgard', 'volstaggasgard@shield.gov', 'volstaggasgardpassword', 1, 5);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Sif', 'Valkyrie', 'sifvalkyrie@shield.gov', 'sifvalkyriepassword', 1, 5);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Drax', 'Destroyer', 'draxdestroyer@shield.gov', 'draxdestroyerpassword', 1, 6);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Gamora', 'Zehoberei', 'gamorazehoberei@shield.gov', 'gamorazehobereipassword', 1, 6);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Rocket', 'Raccoon', 'rocketraccoon@shield.gov', 'rocketraccoonpassword', 1, 6);

INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Groot', 'Groot', 'groot@shield.gov', 'grootpassword', 1, 6);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Natasha', 'Romanoff', 'natasharomanoff@shield.gov', 'natasharomanoffpassword', 2, 7);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('James', 'Rhodes', 'jamesrhodes@shield.gov', 'jamesrhodespassword', 2, 7);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Samuel', 'Wilson', 'samuelwilson@shield.gov', 'samuelwilsonpassword', 2, 7);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Wanda', 'Maximoff', 'wandamaximoff@shield.gov', 'wandamaximoffpassword', 2, 7);

INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Vision', 'Jarvis', 'visionjarvis@shield.gov', 'visionjarvispassword', 2, 7);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Melinda', 'May', 'melindamay@shield.gov', 'melindamaypassword', 2, 8);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Leo', 'Fitz', 'leofitz@shield.gov', 'leofitzpassword', 2, 8);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Jemma', 'Simmons', 'jemmasimmons@shield.gov', 'jemmasimmonspassword', 2, 8);
INSERT INTO Employee (FirstName, LastName, Email, EmployeePassword, Department, DirectSupervisor) VALUES ('Skye', 'Johnson', 'skyejohnson@shield.gov', 'skyejohnsonpassword', 2, 8);

INSERT INTO BenCo (BenCoEmployee) VALUES ('1');

INSERT INTO DepartmentHead (DHDepartment, DHEmployee) VALUES (1, 2);
INSERT INTO DepartmentHead (DHDepartment, DHEmployee) VALUES (2, 3);
INSERT INTO DepartmentHead (DHDepartment, DHEmployee) VALUES (3, 1);