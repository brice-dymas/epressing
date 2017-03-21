'use strict';

describe('Controller Tests', function() {

    describe('LigneCommande Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLigneCommande, MockProduit, MockCommande, MockOperation, MockCaracteristique;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLigneCommande = jasmine.createSpy('MockLigneCommande');
            MockProduit = jasmine.createSpy('MockProduit');
            MockCommande = jasmine.createSpy('MockCommande');
            MockOperation = jasmine.createSpy('MockOperation');
            MockCaracteristique = jasmine.createSpy('MockCaracteristique');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LigneCommande': MockLigneCommande,
                'Produit': MockProduit,
                'Commande': MockCommande,
                'Operation': MockOperation,
                'Caracteristique': MockCaracteristique
            };
            createController = function() {
                $injector.get('$controller')("LigneCommandeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'epressingApp:ligneCommandeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
