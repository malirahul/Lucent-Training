import React, { useState } from "react";
import { Form, FormLayout, TextField, Button } from "@shopify/polaris";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function AddCustomer() {
  const navigate = useNavigate();

  const [first_name, setFirstName] = useState("");
  const [last_name, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setMobileNo] = useState("");
  const [company, setCompany] = useState("");
  const [address1, setAddress1] = useState("");
  const [city, setCity] = useState("");
  const [province, setProvince] = useState("");
  const [country, setCountry] = useState("");
  const [zip, setZip] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();

    const customerData = {
      first_name,
      last_name,
      email,
      phone,
      addresses: [
        {
          first_name,
          last_name,
          company,
          address1,
          city,
          name: `${first_name} ${last_name}`,
          province,
          zip,
          country,
          phone,
        },
      ],
    };

    axios
      .post("http://localhost:3000/createCustomer", customerData, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${sessionStorage.getItem("authToken")}`,
        },
      })
      .then((response) => {
        console.log(response.data);
        alert("Customer created successfully!");
        navigate("/customers");
      })
      .catch((error) => {
        console.error(error);
        alert("Error creating customer.");
      });

    setFirstName("");
    setLastName("");
    setEmail("");
    setMobileNo("");
    setCompany("");
    setAddress1("");
    setCity("");
    setZip("");
    setProvince("");
    setCountry("");
  };

  return (
    <div style={{ maxWidth: "500px", margin: "0 auto" }}>
      <Form onSubmit={handleSubmit}>
        <FormLayout>
          <TextField
            label="First Name"
            value={first_name}
            onChange={(value) => setFirstName(value)}
            required
          />
          <TextField
            label="Last Name"
            value={last_name}
            onChange={(value) => setLastName(value)}
            required
          />
          <TextField
            label="Email"
            type="email"
            value={email}
            onChange={(value) => setEmail(value)}
            required
          />
          <TextField
            label="Mobile No"
            type="tel"
            value={phone}
            onChange={(value) => setMobileNo(value)}
            required
          />
          <TextField
            label="Company"
            value={company}
            onChange={(value) => setCompany(value)}
            required
          />
          <TextField
            label="Address"
            value={address1}
            onChange={(value) => setAddress1(value)}
            required
          />
          <TextField
            label="City"
            value={city}
            onChange={(value) => setCity(value)}
            required
          />
          <TextField
            label="Zip"
            value={zip}
            onChange={(value) => setZip(value)}
          />
          <TextField
            label="State"
            value={province}
            onChange={(value) => setProvince(value)}
            required
          />
          <TextField
            label="Country"
            value={country}
            onChange={(value) => setCountry(value)}
            required
          />
          <Button primary submit>
            Create Customer
          </Button>
        </FormLayout>
      </Form>
    </div>
  );
}

export default AddCustomer;
