# EZCart E-Commerce Platform - GitHub Repository

## Project Overview
EZCart is a user-friendly, scalable, and secure e-commerce platform designed to:
- Help businesses easily create and manage online stores
- Provide customers with a seamless shopping experience
- Simplify order management, payment processing, and inventory tracking

**Course:** SCD  
**Submitted to:** Maam Fatima Gillani  
**Submitted by:** 
- Adil Bilal (FL21499)
- Sana Bushra (FL21526)  
**Section:** BSSE-5B

## Key Features
### For Customers:
- User registration and login
- Product browsing with category filters
- Shopping cart functionality
- Secure checkout and payment processing
- Order tracking

### For Businesses (Admin):
- Product catalog management
- Inventory control
- Order processing
- Customer management
- Sales analytics

## Technical Specifications
- **Frontend:** Java GUI (Swing)
- **Backend:** Java
- **Version Control:** Git/GitHub
- **Architecture:** MVC pattern

## Implementation Highlights
- Modular code structure with proper separation of concerns
- Comprehensive error handling and input validation
- Clean GUI design with intuitive navigation
- Sample product data for demonstration
- Detailed code documentation

## Testing Results
The application has been thoroughly tested with:
- 10 test cases covering core functionality
- Bug tracking and resolution
- Validation of both functional and non-functional requirements

## How to Run
1. Ensure you have Java JDK installed
2. Clone this repository
3. Compile and run `EZCartApp.java`
4. Use test credentials (any non-empty username/password meeting requirements)

## Known Issues
- Stock quantity doesn't decrease when items are added to cart (Bug EC-01)
- Same product can be added multiple times as separate items (Bug EC-02)
- Payment succeeds even for $0 or negative amounts (Bug EC-03)
- No stock check before adding to cart (Bug EC-04)

## Future Improvements
- Database integration for persistent storage
- Actual payment gateway implementation
- Enhanced admin functionality
- Multi-threading support
- Mobile responsiveness

## Documentation
The project includes comprehensive documentation:
- Software Requirements Specification (SRS)
- UML diagrams (Use Case, Object, Sequence, Timing, Petri Nets)
- Implementation and testing reports
- Code comments (JavaDoc, TODO markers, amplification comments)

## Contribution
This project was developed by:
- Adil Bilal (FL21499) - Admin Panel, Payment System, GUI Development, UX Improvements
- Sana Bushra (FL21526) - Authentication, Product Management, Cart, Order Processing

## License
Copyright © 2025 EZCart. All rights reserved.
