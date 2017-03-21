(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('TypeUtilisateurDialogController', TypeUtilisateurDialogController);

    TypeUtilisateurDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeUtilisateur'];

    function TypeUtilisateurDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeUtilisateur) {
        var vm = this;

        vm.typeUtilisateur = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.typeUtilisateur.id !== null) {
                TypeUtilisateur.update(vm.typeUtilisateur, onSaveSuccess, onSaveError);
            } else {
                TypeUtilisateur.save(vm.typeUtilisateur, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('epressingApp:typeUtilisateurUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
