import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { IndexTable, LegacyCard, Badge, Button } from "@shopify/polaris";

export default function Orders() {
  const [orders, setOrders] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    loadOrders();
  }, []);

  const loadOrders = async () => {
    try {
      const result = await axios.get("http://localhost:3000/orders", {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + sessionStorage.getItem("authToken"),
        },
      });
      var prod = [];
      prod = result.data;
      setOrders(prod);
      console.log("data", prod);
      console.log("size", prod.length);
    } catch (error) {
      console.log(error);
    }
  };

  const deleteOrder = async (id) => {
    console.log(id);
    if (window.confirm("Confirm Delete?")) {
      const result = await axios.delete(
        "http://localhost:3000/deleteOrders/" + id,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + sessionStorage.getItem("authToken"),
          },
        }
      );
      loadOrders();
    }
  };

  function addOrderForm() {
    console.log("Going add Order");
    navigate("/createOrder");
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
          Orders
        </h1>
        <Button
          onClick={() => {
            addOrderForm();
          }}
          primary
        >
          Create Order
        </Button>
      </div>

      <div className="container">
        <div className="py-4">
          <IndexTable
            resourceName={{ singular: "order", plural: "orders" }}
            itemCount={orders.length}
            headings={[
              { title: <span style={{ fontWeight: "bold" }}>Order</span> },
              { title: <span style={{ fontWeight: "bold" }}>Email</span> },
              { title: <span style={{ fontWeight: "bold" }}>Total</span> },
              {
                title: (
                  <span style={{ fontWeight: "bold" }}>Payment Status</span>
                ),
              },
              { title: <span style={{ fontWeight: "bold" }}>Tags</span> },
              {
                title: (
                  <span style={{ fontWeight: "bold" }}>Fulfillment Status</span>
                ),
              },
              <br />,
              { title: <span style={{ fontWeight: "bold" }}>Actions</span> },
            ]}
          >
            {orders.map((data, index) => (
              <IndexTable.Row key={index}>
                <IndexTable.Cell>{data.name}</IndexTable.Cell>
                <IndexTable.Cell>{data.email}</IndexTable.Cell>
                <IndexTable.Cell>{data.current_total_price}</IndexTable.Cell>
                <IndexTable.Cell>
                  <Badge
                    status={
                      data.financial_status === "paid" ? "success" : "warning"
                    }
                  >
                    {data.financial_status}
                  </Badge>
                </IndexTable.Cell>
                <IndexTable.Cell>{data.tags}</IndexTable.Cell>
                <IndexTable.Cell>
                  <Badge
                    status={
                      data.fulfillment_status === "fulfilled"
                        ? "success"
                        : "warning"
                    }
                  >
                    {data.fulfillment_status}
                  </Badge>
                </IndexTable.Cell>

                <IndexTable.Cell>{data.fulfillmentStatus}</IndexTable.Cell>
                <IndexTable.Cell>
                  <div className="action-buttons">
                    <Button primary>Fulfillment</Button>
                    <Button destructive onClick={() => deleteOrder(data.id)}>
                      Delete
                    </Button>
                  </div>
                </IndexTable.Cell>
              </IndexTable.Row>
            ))}
          </IndexTable>
        </div>
      </div>
    </div>
  );
}
