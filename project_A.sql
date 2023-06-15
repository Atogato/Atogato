DROP TABLE SITEUSER CASCADE;

create table Hello(
	id INT NOT NULL AUTO_INCREMENT,
	CONSTRAINT Hello_PK PRIMARY KEY(id)
);

select * from hello;
select * from SITEUSER;
use backend;

CREATE TABLE SITEUSER (
	 ID INT NOT NULL AUTO_INCREMENT,
	 USERID VARCHAR(40) NOT NULL,
     USERNAME VARCHAR(40) NOT NULL , 
	 PASSWORD  VARCHAR(200)  NOT NULL,
     ARTISTTYPE VARCHAR(20) NOT NULL , 
     USERTYPE VARCHAR(10) DEFAULT 'USER',
     CONSTRAINT USER_ID_PK  PRIMARY KEY(ID),
     CONSTRAINT USER_USERNAME_UQ 	UNIQUE (USERNAME),
     CONSTRAINT USER_USERID_UQ 	UNIQUE (USERID),
     CONSTRAINT USERTYPE_TYPE_CK CHECK (ARTISTTYPE IN ('연기', '노래', '제작', '춤', '작곡', '기타')),
     CONSTRAINT USER_USER_TYPE_CK CHECK (USERTYPE IN ('USER', 'ADMIN'))
);

INSERT INTO SITEUSER(USERID, USERNAME, PASSWORD,  ARTISTTYPE)
 VALUES('rudgusee', 'kim@naver.com',  '1234', '연기');

INSERT INTO SITEUSER(USERID, USERNAME, PASSWORD,  ARTISTTYPE)
 VALUES( 'hmson', 'hmson@naver.com', '5678', '노래');
