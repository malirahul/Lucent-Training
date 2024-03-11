import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useNavigate, useParams } from "react-router-dom";
import { Button, Tag, Badge, DataTable, Icon } from "@shopify/polaris";

export default function Products() {
  const [products, setData] = useState([]);
  const navigate = useNavigate();

  const { id } = useParams();

  useEffect(() => {
    loadProducts();
  }, []);

  console.log("Token : ", sessionStorage.getItem("authToken"));
  const loadProducts = async () => {
    try {
      const result = await axios.get("http://localhost:3000/products", {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + sessionStorage.getItem("authToken"),
        },
      });
      setData(result.data);
      console.log(result);
    } catch (error) {
      console.log(error);
    }
  };

  const deleteProduct = async (id) => {
    console.log(id);
    if (window.confirm("Confirm Delete?")) {
      const result = await axios.delete(
        "http://localhost:3000/deleteProduct/" + id,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + sessionStorage.getItem("authToken"),
          },
        }
      );

      loadProducts();
    }
  };

  function addProductForm() {
    console.log("Going add Product");
    navigate("/addProduct");
  }

  function editProductForm(id) {
    console.log("Going to edit Product");
    navigate("/product/editProduct/" + id);
  }
  function viewProductForm(id) {
    console.log("Going to view Product");
    navigate("/product/viewProduct/" + id);
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
          Products
        </h1>
        <Button
          onClick={() => {
            addProductForm();
          }}
          primary
        >
          Add Product
        </Button>
      </div>
      <div className="container">
        <DataTable
          columnContentTypes={[
            "numeric",
            "text",
            "text",
            "text",
            "text",
            "text",
          ]}
          headings={[
            <span style={{ fontWeight: "bold" }}>#</span>,
            <span style={{ fontWeight: "bold" }}>Product</span>,
            <span style={{ fontWeight: "bold" }}>Status</span>,
            <span style={{ fontWeight: "bold" }}>Type</span>,
            <span style={{ fontWeight: "bold" }}>Vendor</span>,
            <span style={{ fontWeight: "bold" }}>Action</span>,
          ]}
          rows={products.map((data, index) => [
            index + 1,
            <div
              onClick={() => viewProductForm(data.id)}
              style={{ color: "blue", cursor: "pointer" }}
            >
              {data.title}
            </div>,
            <Badge status={data.status === "active" ? "success" : "critical"}>
              {data.status}
            </Badge>,
            data.product_type,
            data.vendor,
            <div className="action-buttons">
              <Button onClick={() => editProductForm(data.id)} primary>
                Edit
              </Button>
              <Button onClick={() => deleteProduct(data.id)} destructive>
                Delete
              </Button>
            </div>,
          ])}
        />
      </div>
    </div>
  );
}
