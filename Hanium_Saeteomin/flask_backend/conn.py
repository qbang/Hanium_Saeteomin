import psycopg2
import psycopg2.extras

class Postgresql(object):
    def __init__(self):
        self.connect()

    def connect(self):
        self.db = psycopg2 .connect(host="database-1.cxlhg3zegkzd.ap-northeast-2.rds.amazonaws.com",
                              user="postgres",
                              password="0987654321",
                              port = "5432",
                              dbname="postgres")
        self.cursor = self.db.cursor(cursor_factory=pg2.extras.DictCursor)
        self.commit = self.db.commit()

    def close(self):
        self.db.close()