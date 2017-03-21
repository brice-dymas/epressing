(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('LigneCommandeDialogController', LigneCommandeDialogController);

    LigneCommandeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LigneCommande', 'Produit', 'Commande', 'Operation', 'Caracteristique'];

    function LigneCommandeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LigneCommande, Produit, Commande, Operation, Caracteristique) {
        var vm = this;

        vm.ligneCommande = entity;
        vm.clear = clear;
        vm.save = save;
        vm.produits = Produit.query();
        vm.commandes = Commande.query();
        vm.operations = Operation.query();
        vm.caracteristiques = Caracteristique.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ligneCommande.id !== null) {
                LigneCommande.update(vm.ligneCommande, onSaveSuccess, onSaveError);
            } else {
                LigneCommande.save(vm.ligneCommande, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('epressingApp:ligneCommandeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
