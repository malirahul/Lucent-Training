import { Page, Form, FormLayout, Button, Layout } from "@shopify/polaris";
import { useCallback } from "react";

function LogOut() {
  const handleSubmit = useCallback(() => {
    console.log("Going for Login");
    window.location.href = "http://localhost:3000/login";
    sessionStorage.clear();
  }, []);

  return (
    <Page>
      <Layout>
        <Layout.Section>
          <div
            style={{
              backgroundColor: "#FFF",
              borderRadius: "4px",
              boxShadow: "0px 2px 2px rgba(0, 0, 0, 0.08)",
              padding: "2rem",
            }}
          >
            <p>Are you sure you want to log out?</p>
          </div>
        </Layout.Section>
        <Layout.Section>
          <Form onSubmit={handleSubmit}>
            <Button submit>Log Out</Button>
          </Form>
        </Layout.Section>
      </Layout>
    </Page>
  );
}

export default LogOut;
