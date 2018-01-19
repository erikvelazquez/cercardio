(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicDialogController', MedicDialogController);

    MedicDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Medic', 'UserBD', 'Company', 'EthnicGroup', 'Gender', 'Category'];

    function MedicDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Medic, UserBD, Company, EthnicGroup, Gender, Category) {
        var vm = this;

        vm.medic = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.userbds = UserBD.query({filter: 'medic-is-null'});
        $q.all([vm.medic.$promise, vm.userbds.$promise]).then(function() {
            if (!vm.medic.userBD || !vm.medic.userBD.id) {
                return $q.reject();
            }
            return UserBD.get({id : vm.medic.userBD.id}).$promise;
        }).then(function(userBD) {
            vm.userbds.push(userBD);
        });
        vm.companies = Company.query();
        vm.ethnicgroups = EthnicGroup.query();
        vm.genders = Gender.query();
        vm.categories = Category.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.medic.id !== null) {
                Medic.update(vm.medic, onSaveSuccess, onSaveError);
            } else {
                Medic.save(vm.medic, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:medicUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdat = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
