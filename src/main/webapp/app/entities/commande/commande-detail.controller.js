(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('CommandeDetailController', CommandeDetailController);

    CommandeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Commande', 'CarteBancaire', 'Utilisateur'];

    function CommandeDetailController($scope, $rootScope, $stateParams, previousState, entity, Commande, CarteBancaire, Utilisateur) {
        var vm = this;

        vm.commandeForm = entity;
        vm.ligneCommandes = vm.commandeForm.ligneCommandes;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epressingApp:commandeUpdate', function(event, result) {
            vm.commande = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
