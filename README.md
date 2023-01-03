# PushNotificataionApp

//demo setup for sending the notification from the postman or any other backend service

url for sending notification is  ->  https://fcm.googleapis.com/fcm/send
this is actually a POST API hence the body is below like this ,

{
    "to": "Reciever token that is generated and saved somewhere elese to identify the device",
    "data": {
        // sample data anything you can set here and but these data handled in this app
        "id": "145250967676",
        "image": "https://images.unsplash.com/photo-1559563362-c667ba5f5480?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8Mnx8fGVufDB8fHx8&w=1000&q=80",
        "text": "A think of beauty is joy for ever",
        "featuresAvailable_1": "true",
        "featuresAvailable_2": "true",
        "featuresAvailable_3": "false",
        "featuresAvailable_4": "true"
    }
}

For Authorization 
Authorization  : Key="Server key generated from firebase console from the gear>cloudmessaging>serverkey"

Thats all you can now get your notification inside the app

Thank You
Subham Bikash Behera
