import { DescriptionList, Divider, LegacyCard, Page } from "@shopify/polaris";
import axios from "axios";
import React, { useEffect, useState } from "react";

export default function HealthComponent() {
  const [health, setHealth] = useState([]);

  useEffect(() => {
    loadHealth();
  }, []);

  const loadHealth = async () => {
    try {
      const result = await axios.get("http://localhost:3000/actuator/health", {
        headers: {
          "Content-Type": "application/json",
        },
      });
      setHealth(result.data);
      console.log("data", result.data);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div>
      <Page title="Health">
        <LegacyCard>
          <DescriptionList
            items={[
              {
                term: "Health",

                description: health.status,
              },
              <Divider />,
              {
                term: "Disk Space",
                description:
                  (
                    health.components.diskSpace.details.free /
                    1024 /
                    1024 /
                    1024
                  ).toFixed(2) + " GB",
              },
            ]}
          />
        </LegacyCard>
      </Page>
    </div>
  );
}
