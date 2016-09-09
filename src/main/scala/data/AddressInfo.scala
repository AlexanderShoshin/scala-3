package data

case class AddressInfo(registrationCity: String,
                       livingCity: String,
                       postalCity: String,
                       loanCity: String,
                       state: String,
                       registrationResidenceMatch: String,
                       residenceMailMatch: String,
                       registrationMailMatch: String,
                       postalMatch: String)