(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('TarifDialogController', TarifDialogController);

    TarifDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tarif', 'Operation', 'Produit'];

    function TarifDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tarif, Operation, Produit) {
        var vm = this;

        vm.tarif = entity;
        vm.clear = clear;
        vm.save = save;
        vm.operations = Operation.query();
        vm.produits = Produit.findAll();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tarif.id !== null) {
                Tarif.update(vm.tarif, onSaveSuccess, onSaveError);
            } else {
                Tarif.save(vm.tarif, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('epressingApp:tarifUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
