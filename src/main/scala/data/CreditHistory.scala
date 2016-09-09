package data

case class CreditHistory(loanAddress: String,
                         lastLoan: String,
                         loanTerm: String,
                         downPayment: String,
                         loansNum: String,
                         closedLoansNum: String,
                         donePaymentsNum: String,
                         paymentDelaysNum: String,
                         maxOrderNumber: String,
                         averagePaymentDelay: String,
                         maxPaymentDelay: String)