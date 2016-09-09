package data

case class Client(personalInfo: PersonalInfo,
                  addressInfo: AddressInfo,
                  property: Property,
                  creditHistory: CreditHistory)

object Client {
  def apply(input: String): Client = {
    val data = input.split(";")
    val creditHistory = CreditHistory(
      loanAddress = data(24),
      lastLoan = data(32),
      loanTerm = data(33),
      downPayment = data(34),
      loansNum = data(42),
      closedLoansNum = data(43),
      donePaymentsNum = data(44),
      paymentDelaysNum = data(45),
      maxOrderNumber = data(46),
      averagePaymentDelay = data(47),
      maxPaymentDelay = data(48))
    val property = Property(
      apartmentOwner = data(25),
      carsNum = data(26),
      patriotCar = data(27),
      outCityHouse = data(28),
      cottage = data(29),
      garage = data(30),
      land = data(31)
    )
    val addressInfo = AddressInfo(
      registrationCity = data(15),
      livingCity = data(16),
      postalCity = data(17),
      loanCity = data(18),
      state = data(19),
      registrationResidenceMatch = data(20),
      residenceMailMatch = data(21),
      registrationMailMatch = data(22),
      postalMatch = data(23)
    )
    val personalInfo = PersonalInfo(
      age = data(0),
      isEmployed = data(1),
      isRetired = data(2),
      sex = data(3),
      children = data(4),
      dependentPeople = data(5),
      education = data(6),
      maritalStatus = data(7),
      profArea = data(8),
      profPos = data(9),
      companyOwnership = data(10),
      foreignCapital = data(11),
      profActivity = data(12),
      familyIncome = data(13),
      personalIncome = data(14),
      driverLicence = data(35),
      pensionFound = data(36),
      homeLivingDuration = data(37),
      lastWorkDuration = data(38),
      hasResidencePhone = data(39),
      hasRegistrationPhone = data(40),
      hasWorkPhone = data(41)
    )
    Client(
      personalInfo,
      addressInfo,
      property,
      creditHistory
    )
  }
}