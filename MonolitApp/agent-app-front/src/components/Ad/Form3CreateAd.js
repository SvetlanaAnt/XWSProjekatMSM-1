import React from 'react';
import { Form, Col, Container, Button, InputGroup, Card, ButtonGroup, Table } from 'react-bootstrap';
import { Typography } from '@material-ui/core';

const Form3CreateAd = (props) => {
    return (
        <Container>
            <Form id="step3" onSubmit={props.onSubmit} noValidate validated={props.validated}>
                <Form.Row>
                    <Col>
                        <ButtonGroup variant="outline" >
                            <Button onClick={props.handleActiveToggle0}>Izaberi cenovnik</Button>
                            <Button onClick={props.handleActiveToggle1}>Dodaj cenovnik</Button>
                        </ButtonGroup>
                        <br />
                        <br />
                    </Col>
                </Form.Row>
                <Form.Row>
                    {props.activeToggle === 0 ?
                        <Col>
                            <Table striped bordered hover>
                                <thead>
                                    <tr>
                                        <th>Rbr.</th>
                                        <th className="text-right">Cena po danu</th>
                                        <th className="text-right">Cena po km</th>
                                        <th className="text-right">Cena po km (CDW)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {/* {
                                                props.carManufacturers.map((carManufacturer) => {
                                                    return (
                                                        <tr key={carManufacturer.id}>
                                                            <td>{carManufacturer.name}</td>
                                                            <td align="right">
                                                                <Button variant="outline-success" onClick={() => { props.handleEdit(carManufacturer); }}>Izmjeni</Button>
                                                            </td>
                                                            <td align="right">
                                                                <Button variant="outline-danger" onClick={() => { props.handleDelete(carManufacturer.id); }}>Obriši</Button>
                                                            </td>
                                                        </tr>
                                                    );
                                                })
                                            } */}
                                    {/* <Form.Group as={Col}>
                                        <Form.Label>Check box za biranje vec postojeceg cenovnika</Form.Label>
                                        <Form.Control name="id" id="selectId" placeholder="Cena po danu" as="select" type="text" >
                                            <option>1</option>
                                            <option>2</option>
                                            <option>3</option>
                                        </Form.Control>
                                    </Form.Group> */}
                                </tbody>
                            </Table>
                        </Col>
                        : null
                    }
                    {props.activeToggle === 1 ?
                        <Col>
                            <Form.Group as={Col}>
                                <Form.Label>Cena po danu</Form.Label>
                                <InputGroup className="mb-3">
                                    <InputGroup.Prepend>
                                        <InputGroup.Text>RSD</InputGroup.Text>
                                    </InputGroup.Prepend>
                                    <Form.Control name="pricePerDay" required id="numPricePerDay"
                                        type="number" placeholder="Cena po danu" />
                                    <InputGroup.Append>
                                        <InputGroup.Text>.00</InputGroup.Text>
                                    </InputGroup.Append>
                                </InputGroup>
                            </Form.Group>
                            {props.distanceLimitFlag ?
                                <Form.Group as={Col}>
                                    <Form.Label>Cena po kilometru</Form.Label>
                                    <InputGroup className="mb-3">
                                        <InputGroup.Prepend>
                                            <InputGroup.Text>RSD</InputGroup.Text>
                                        </InputGroup.Prepend>
                                        <Form.Control name="pricePerKm" required id="numPricePerKm"
                                            type="number" placeholder="Cena po kilometru" onChange={props.handlePricePerKm} />
                                        <InputGroup.Append>
                                            <InputGroup.Text>.00</InputGroup.Text>
                                        </InputGroup.Append>
                                    </InputGroup>
                                </Form.Group>
                                : null
                            }
                            {props.cdw ?
                                <Form.Group as={Col}>
                                    <Form.Label>Cena po kilometru (CDW)</Form.Label>
                                    <InputGroup className="mb-3">
                                        <InputGroup.Prepend>
                                            <InputGroup.Text>RSD</InputGroup.Text>
                                        </InputGroup.Prepend>
                                        <Form.Control name="pricePerKmCDW" required id="numPricePerKmCDW"
                                            type="number" placeholder="Cena po kilometru (CDW)" onChange={props.handlePricePerKmCDW} />
                                        <InputGroup.Append>
                                            <InputGroup.Text>.00</InputGroup.Text>
                                        </InputGroup.Append>
                                    </InputGroup>
                                </Form.Group>
                                : null
                            }
                        </Col>
                        : null
                    }
                </Form.Row>
                <Form.Row>
                    <Col>
                        <Form.Group >
                            {props.activeStep === props.steps.length ? (
                                <div >
                                    <Typography>
                                        Svi koraci su zavrseni. Uspesno ste dodadali oglas!
                                    </Typography>

                                    <Button onClick={props.handleReset}>
                                        Reset
                                    </Button>
                                </div>
                            ) : (
                                    <div >

                                        <Button disabled={props.activeStep === 0} onClick={props.handleBack}
                                            className="float-left">
                                            Nazad
                                        </Button>

                                        {props.isStepOptional(props.activeStep) && (
                                            <Button
                                                variant="contained"
                                                onClick={props.handleSkip}
                                                className="float-right"
                                            >
                                                Preskoci
                                            </Button>
                                        )}

                                        <Button type="submit" className="float-right">
                                            Dalje
                                        </Button>

                                    </div>
                                )}
                        </Form.Group>
                    </Col>
                </Form.Row>
            </Form>
        </Container>
    );
}
export default Form3CreateAd;
