# You Owe Me - backend
## Requirements
### Functional
- User can do signup, login, and password reset with auditing
- An email should send when the user signup and resets their password
- User can delete its account with GDPR right
- There should be the persistence cookie-based remember-me feature
- The creditor user can create a receipt with these fields:
    | Field name   | Mandatory |
    |:-------------| :--------:|
    | Receipt name |     ❌    |
    | Items list   |     ✅    |
    | Items price  |     ✅    |
    | Currency     |     ✅    |
    | DateTime     |     ✅    |
    | Location     |     ❌    |
    | Users        |     ✅    |
- When the creditor created the receipt it sets to the initial state
- When the creditor sets the receipt's state to confirmed state, the invitation link will send to debtors
- Users can see the inbox of receipts inside their dashboard
- Debtors will receive the link to the receipts with full details
- Users can make network with each other
- There should be a comment section inside each receipt
- Creditors can select debtors from their network or mention their username
- Debtor can set the receipt to paid state

### Non-functional
- Users can experience a well-designed home page
- Users can see a simple and user-friendly dashboard page
- Users are inactive by default then an email will send to the user's email for account activation
- When the user resets its password an email should be sent to user's email
- User's authentication will be implemented with Spring Security JWT
