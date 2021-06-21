from flask import Flask, jsonify,request
import smtplib  as smtp 
from email.message import EmailMessage



app = Flask(__name__)
user = 'rickysharmamailbox@gmail.com'
password = '+++++'
fr_address = 'rickysharmamailbox@gmail.com'
to_address = 'getwellpsit@gmail.com'
smtp_host = 'smtp.gmail.com' 
smtp_port = 587


@app.route('/mailto/<num>',methods=['GET','POST'])  # Takes input Email Address 
def get_check(num):
    s=smtp.SMTP(smtp_host,smtp_port)
    s.starttls()
    s.login(user, password)
    message = "Make appointment for "+num
    s.sendmail(fr_address, to_address, message)
    s.quit()


    return "Sent mail"

if __name__=='__main__':
    app.run(debug=True)
