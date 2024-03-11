import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import { Button, Form, TextField } from "@shopify/polaris";

export default function EditCustomer() {
  const [customer, setCustomer] = useState({});
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const loadCustomer = async () => {
      try {
        const result = await axios.get(
          `http://localhost:3000/customers/${id}`,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: "Bearer " + sessionStorage.getItem("authToken"),
            },
          }
        );
        setCustomer(result.data);
        console.log(result.data);
        setFirstName(result.data.first_name);
        console.log(result.data.first_name);
        setLastName(result.data.last_name);
        setEmail(result.data.email);
        setPhone(result.data.phone);
      } catch (error) {
        console.error(error);
      }
    };
    loadCustomer();
  }, [id]);

  const handleSubmit = async () => {
    try {
      const result = await axios.put(
        `http://localhost:3000/updateCustomer/${id}`,
        {
          first_name: firstName,
          last_name: lastName,
          email: email,
          phone: phone,
        },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + sessionStorage.getItem("authToken"),
          },
        }
      );
      console.log(result.data);
      navigate("/customers");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div>
      <h1 style={{ fontSize: "36px", fontWeight: "bold", color: "#333" }}>
        Customer
      </h1>
      <div className="container">
        <Form onSubmit={handleSubmit}>
          <TextField
            label="First Name"
            value={firstName}
            onChange={(value) => setFirstName(value)}
          />
          <TextField
            label="Last Name"
            value={lastName}
            onChange={(value) => setLastName(value)}
          />
          <TextField
            label="Email"
            value={email}
            onChange={(value) => setEmail(value)}
          />
          <TextField
            label="Phone"
            value={phone}
            onChange={(value) => setPhone(value)}
          />
          <Button submit>Save</Button>
        </Form>
      </div>
    </div>
  );
}
