import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useNavigate, useParams } from "react-router-dom";
import {
  Button,
  ResourceList,
  ResourceItem,
  Tag,
  Badge,
  DataTable,
  Icon,
} from "@shopify/polaris";

export default function Customer() {
  const [customers, setData] = useState([]);
  const navigate = useNavigate();

  const { id } = useParams();

  useEffect(() => {
    loadCustomers();
  }, []);

  console.log("Token : ", sessionStorage.getItem("authToken"));
  const loadCustomers = async () => {
    try {
      const result = await axios.get("http://localhost:3000/customers", {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + sessionStorage.getItem("authToken"),
        },
      });
      setData(result.data);
      console.log(result.data);
    } catch (error) {
      console.error(error);
    }
  };

  const deleteCustomer = async (id) => {
    console.log(id);
    if (window.confirm("Are you sure you want to delete this customer?")) {
      const result = await axios.delete(
        "http://localhost:3000/deleteCustomer/" + id,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + sessionStorage.getItem("authToken"),
          },
        }
      );

      loadCustomers();
    }
  };

  function addCustomerForm() {
    console.log("Going add Customer");
    navigate("/addCustomer");
  }

  function editCustomerForm(id) {
    console.log("Going to edit Customer");
    navigate("/customer/editCustomer/" + id);
  }
  function viewCustomerForm(id) {
    console.log("Going to view Customer");
    navigate("/customer/viewCustomer/" + id);
  }

  return (
    <div>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <h1 style={{ fontSize: "36px", fontWeight: "bold", color: "#333" }}>
          Customers
        </h1>
        <Button
          onClick={() => {
            addCustomerForm();
          }}
          primary
        >
          Add Customer
        </Button>
      </div>
      <div className="container">
        <DataTable
          columnContentTypes={[
            "numeric",
            "text",
            "text",
            "text",
            "numeric",
            "text",
            "text",
          ]}
          headings={[
            <span style={{ fontWeight: "bold" }}>#</span>,
            <span style={{ fontWeight: "bold" }}>Name</span>,
            <span style={{ fontWeight: "bold" }}>Email</span>,
            <span style={{ fontWeight: "bold" }}>Phone</span>,
            <span style={{ fontWeight: "bold" }}>state</span>,
            <span style={{ fontWeight: "bold" }}>Action</span>,
          ]}
          rows={customers.map((data, index) => [
            index + 1,
            <div
              onClick={() => viewCustomerForm(data.id)}
              style={{ color: "blue", cursor: "pointer" }}
            >
              {data.first_name + " " + data.last_name}
            </div>,
            data.email,
            data.phone,
            data.state,
            <div className="action-buttons">
              <Button onClick={() => editCustomerForm(data.id)} primary>
                Edit
              </Button>
              <Button onClick={() => deleteCustomer(data.id)} destructive>
                Delete
              </Button>
            </div>,
          ])}
        />
      </div>
    </div>
  );
}
