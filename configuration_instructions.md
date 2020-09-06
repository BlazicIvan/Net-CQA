# Analysis configuration

Configuration is changed by editing the JSON files in `/resources/config` directory.  
Please read the [introduction](introduction.md) before proceeding with the configuration.

## Global configuration

### Default verbose output
Enables a more detailed console output by default.

File: `/settings.json`  
Variable: `logging_enabled` (boolean)


### Max quality grade
Defines the maximal number used for quality grades.
All grades are on a scale from zero to this value.

File: `/settings.json`  
Variable: : `max_grade` (float)

## Metric reference values
These settings define ideal values for each metric based on user's judgement.

File: `/metrics/ref_values.json`  
Variables: JSON objects named by metric ID  
Each object has the following variables:
- Reference value: `reference` (float)
- Strategy: `strategy` (string)

Strategy is used to define how the reference value is interpreted.  
It can have one of the following values:
- `"ref-exceed"` - metric grade gets linearly lower as the real value exceeds the reference value
- `"raw-value"` - metric grade is equal to the actual value (only appliable for metrics with values from 0 to 1)


## Definition of quality properties
Contain user-defined quality properties.  
Quality property is a named weighted sum of metric values, for example "Readability".  
See the default configuration for examples.  

File: `/props/<metric_id>.json`  
Variables:  
- Property name: `title` (string)  
- Array of metric factors (weights): `factors` (JSON array)

Each array entry represents an influence from a single metric, and contains the following variables:
- `factor` - weight of the metric, from 0 to 1 (float)
- `metric` - metric ID (string)

If a metric is missing from the `factors` array, it's influence to the property is zero.
